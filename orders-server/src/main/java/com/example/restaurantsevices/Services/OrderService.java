package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.OrderRepo;
import com.example.restaurantsevices.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public Orders addOrder(Orders order) { return orderRepo.save(order); }
    public List<Orders> getAllOrders() { return orderRepo.findAll(); }
    public Orders getOrderById(Long id) { return orderRepo.findById(id).orElse(null); }
    public void deleteOrderById(Long id) { orderRepo.deleteById(id); }




}
