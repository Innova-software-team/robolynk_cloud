package com.example.restaurantsevices.Repo;
import com.example.restaurantsevices.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Products, Long> {
}
