package com.app.restaurant.serviceimpl.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.admin.UsersRequestDTO;
import com.app.restaurant.dto.admin.UsersResponseDTO;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.admin.UserVerification;
import com.app.restaurant.model.admin.Users;
import com.app.restaurant.repository.admin.UserVerificationRepository;
import com.app.restaurant.repository.admin.UsersRepository;
import com.app.restaurant.service.admin.UsersService;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public UsersResponseDTO createUser(UsersRequestDTO dto) {
        logger.info("Creating user with email: {}", dto.getEmail());

        if (usersRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Email already exists: {}", dto.getEmail());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }
        if (usersRepository.existsByUsername(dto.getUsername())) {
            logger.warn("Username already exists: {}", dto.getEmail());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }
        if (usersRepository.existsByMobile(dto.getMobile())) {
            logger.warn("Mobile number already exists: {}", dto.getMobile());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            logger.warn("Password is missing during user creation for email: {}", dto.getEmail());
            throw new IllegalArgumentException(MessageConstants.COMMON_INVALID);
        }

        Users user = new Users();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setDeleted(false);
        user.setVerified(false);

        Users savedUser = usersRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());

        // Create verification token
        String token = UUID.randomUUID().toString();
        UserVerification verification = new UserVerification();
        verification.setToken(token);
        verification.setUser(savedUser);
        verification.setExpiryDate(LocalDateTime.now().plusHours(24));
        userVerificationRepository.save(verification);
        logger.info("Verification token created for user ID: {}", savedUser.getId());

        // Send verification email
        sendVerificationEmail(savedUser.getEmail(), token);
        logger.info("Verification email sent to: {}", savedUser.getEmail());

        return toResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public UsersResponseDTO updateUser(Integer userId, UsersRequestDTO dto) {
        logger.info("Updating user with ID: {}", userId);

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
                });

        if (!dto.getEmail().equals(user.getEmail()) && usersRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Email already exists during update: {}", dto.getEmail());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }
        if (!dto.getUsername().equals(user.getUsername()) && usersRepository.existsByUsername(dto.getUsername())) {
            logger.warn("Username already exists during update: {}", dto.getEmail());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }
        if (!dto.getMobile().equals(user.getMobile()) && usersRepository.existsByMobile(dto.getMobile())) {
            logger.warn("Mobile number already exists during update: {}", dto.getMobile());
            throw new IllegalArgumentException(MessageConstants.RECORD_ALREADY_EXISTS);
        }

        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setRole(dto.getRole());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            logger.info("Password updated for user ID: {}", userId);
        }

        Users savedUser = usersRepository.save(user);
        logger.info("User updated with ID: {}", userId);

        return toResponseDTO(savedUser);
    }

    @Override
    public Page<UsersResponseDTO> getAllUsers(Boolean enabled, String search, String searchField, Boolean unpaged,
            Pageable pageable) {
        logger.info("Fetching all users. Enabled: {}, search: {}, searchField: {}, unpaged: {}", enabled, search,
                searchField, unpaged);

        Specification<Users> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.equal(root.get("deleted"), false));
            if (enabled != null) {
                predicate = cb.and(predicate, cb.equal(root.get("enabled"), enabled));
            }
            if (search != null && !search.trim().isEmpty() && searchField != null && !searchField.isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                switch (searchField.toLowerCase()) {
                    case "name":
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), searchLower));
                        break;
                    case "username":
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("username")), searchLower));
                        break;
                    case "email":
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), searchLower));
                        break;
                    case "mobile":
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("mobile")), searchLower));
                        break;
                    case "role":
                        predicate = cb.and(predicate,
                                cb.like(cb.lower(root.get("role").as(String.class)), searchLower));
                        break;
                    default:
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), searchLower));
                }
            }
            return predicate;
        };

        Page<Users> page;
        if (Boolean.TRUE.equals(unpaged)) {
            List<Users> all = usersRepository.findAll(spec);
            List<UsersResponseDTO> dtos = all.stream().map(this::toResponseDTO).toList();
            logger.info("Fetched all users without pagination. Count: {}", dtos.size());
            return new PageImpl<>(dtos);
        } else {
            page = usersRepository.findAll(spec, pageable);
            logger.info("Fetched paginated users. Page number: {}, Size: {}", pageable.getPageNumber(),
                    pageable.getPageSize());
            return page.map(this::toResponseDTO);
        }
    }

    @Override
    public UsersResponseDTO getUserById(Integer userId) {
        logger.info("Fetching user by ID: {}", userId);

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
                });

        return toResponseDTO(user);
    }

    @Override
    @Transactional
    public String toggleUserStatus(Integer userId) {
        logger.info("Toggling user status for ID: {}", userId);

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
                });

        user.setEnabled(!user.isEnabled());
        usersRepository.save(user);

        logger.info("User status toggled. User ID: {}, New status enabled: {}", userId, user.isEnabled());
        return user.isEnabled() ? "User activated successfully" : "User deactivated successfully";
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        logger.info("Verifying email with token: {}", token);

        UserVerification verification = userVerificationRepository.findByToken(token)
                .orElseThrow(() -> {
                    logger.error("Invalid or expired verification token: {}", token);
                    return new ResourceNotFoundException(MessageConstants.INVALID_ACCESS_TOKEN);
                });

        if (verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.warn("Verification token expired for token: {}", token);
            throw new IllegalArgumentException(MessageConstants.OTP_EXPIRED);
        }

        Users user = verification.getUser();
        user.setVerified(true);
        usersRepository.save(user);
        userVerificationRepository.delete(verification);

        logger.info("User email verified successfully for user ID: {}", user.getId());
    }

    private void sendVerificationEmail(String email, String token) {
        logger.info("Sending verification email to: {}", email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verify Your Email Address");
        message.setText("Please verify your email by clicking the following link: " +
                "http://localhost:8080/api/admin/users/verify?token=" + token);
        mailSender.send(message);

        logger.info("Verification email sent successfully to: {}", email);
    }

    private UsersResponseDTO toResponseDTO(Users user) {
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        dto.setVerified(user.isVerified());
        return dto;
    }
}
