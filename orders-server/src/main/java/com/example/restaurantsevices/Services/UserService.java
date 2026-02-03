package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.UserRepo;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static UserService instance;

    @Autowired
    private UserRepo userRepo;

    @PostConstruct
    private void initInstance() {
        instance = this;
    }

    public static UserService getInstance() {
        return instance;
    }

    public Optional<User> findById(String userId) {
        return userRepo.findById(userId);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public User getOrCreateUserById(String userId) {
        return userRepo.findById(userId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(userId);
                    newUser.setCurrentCart(new Cart());
                    return userRepo.save(newUser);
                });
    }

    public User getUserById(String userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User createUser(String string) {
        User user = new User();
        user.setId(string);
        user.setCurrentCart(new Cart());
        
        return userRepo.save(user);
    }
}
