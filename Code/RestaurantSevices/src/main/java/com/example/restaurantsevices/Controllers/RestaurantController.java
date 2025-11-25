package com.example.restaurantsevices.Controllers;
import com.example.restaurantsevices.Repo.RestaurantRepo;
import com.example.restaurantsevices.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantRepo restaurantRepo;

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }
}




