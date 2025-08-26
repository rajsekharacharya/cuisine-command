package com.app.restaurant.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.repository.admin.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public MyUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsernameOrEmail(username, username)
                .map(MyUserDetail::new)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND);
                });
    }

}