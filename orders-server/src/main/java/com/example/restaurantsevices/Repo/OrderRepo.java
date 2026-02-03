package com.example.restaurantsevices.Repo;

import com.example.restaurantsevices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
    boolean existsByCart_Id(long cartId);
    Order findByCart_Id(long cartId);
    Order findTopByUser_IdOrderByIdDesc(String userId);
}
