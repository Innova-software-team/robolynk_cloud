package com.example.restaurantsevices.Repo;

import com.example.restaurantsevices.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
