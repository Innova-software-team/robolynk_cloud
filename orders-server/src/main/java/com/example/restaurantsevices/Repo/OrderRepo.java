package com.example.restaurantsevices.Repo;

import com.example.restaurantsevices.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Long> {
}
