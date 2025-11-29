package com.example.orderservices.Services;

import com.example.orderservices.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderService orderService;

    public Orders addOrder(Orders order) {
        return orderService.addOrder(order);
    }
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }
    public Orders getOrderById(Long id) {
        return orderService.getOrderById(id);
    }
    public void deleteOrderById(Long id) {
        orderService.deleteOrderById(id);
    }




}
