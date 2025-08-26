package com.app.restaurant.initializer;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.restaurant.enums.RoleType;
import com.app.restaurant.model.admin.Users;
import com.app.restaurant.model.master.Restaurant;
import com.app.restaurant.repository.admin.UsersRepository;
import com.app.restaurant.repository.master.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private static final Random RANDOM = new Random();

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0 && restaurantRepository.count() > 0) {
            return;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Momo Chitte - Ramrajatala");
        restaurant.setDescription("A place for delicious food");
        restaurant.setCuisine("Multi-Cuisine");
        restaurant.setAddress("70/1, Sastri Narendranath Ganguly Rd, near Betore Heights, Betore, Santragachi, Ichapur, Howrah, West Bengal 711104");
        restaurant.setContact("9876543210");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        for (RoleType role : RoleType.values()) {
            Users user = new Users();
            String username = role.name().toLowerCase();
            String password = username;

            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(capitalize(role.name()));
            user.setEmail(generateRandomEmail(username));
            user.setMobile(generateRandomPhone());
            user.setRole(role);
            user.setVerified(true);
            user.setRestaurant(savedRestaurant);

            userRepository.save(user);
        }
    }

    private String generateRandomEmail(String username) {
        int num = RANDOM.nextInt(10000);
        return username + num + "@example.com";
    }

    private String generateRandomPhone() {
        return "9" + (100000000 + RANDOM.nextInt(900000000)); // 10-digit random starting with 9
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
