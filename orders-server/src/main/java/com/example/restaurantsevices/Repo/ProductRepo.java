package com.example.restaurantsevices.Repo;

import com.example.restaurantsevices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
