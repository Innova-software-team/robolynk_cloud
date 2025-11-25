package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.RestaurantRepo;
import com.example.restaurantsevices.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepo.findById(id).orElse(null);
    }
    public void deleteRestaurantById(Long id) {
        restaurantRepo.deleteById(id);
    }




}
