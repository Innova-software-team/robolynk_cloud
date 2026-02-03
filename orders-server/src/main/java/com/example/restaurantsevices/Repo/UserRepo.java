package com.example.restaurantsevices.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurantsevices.model.User;

public interface UserRepo extends JpaRepository<User, String> {
}
