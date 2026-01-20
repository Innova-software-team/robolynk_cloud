package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.OrderRepo;
import com.example.restaurantsevices.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public Order addOrder(Order order) { return orderRepo.save(order); }
    public List<Order> getAllOrders() { return orderRepo.findAll(); }
    public Order getOrderById(Long id) { return orderRepo.findById(id).orElse(null); }
    public void deleteOrderById(Long id) { orderRepo.deleteById(id); }




}
