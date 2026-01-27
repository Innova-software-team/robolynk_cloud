package com.example.restaurantsevices.Controllers;
import com.example.restaurantsevices.Repo.RestaurantRepo;
import com.example.restaurantsevices.Services.RestaurantService;
import com.example.restaurantsevices.model.Product;
import com.example.restaurantsevices.model.Restaurant;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private RestaurantService restaurantService;
   // Unsure what get menu is need to discuss this more
   // @GetMapping
   // public List<Product> getRestaurants() {return }

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @GetMapping("/nearby")
    public PlacesSearchResponse PlaceSearchResponse(@RequestParam String address, @RequestParam(defaultValue = "1000") int radius) throws Exception {
        try{
            return restaurantService.getNearbyRestaurants(address, radius);

        } catch (Exception e){
            throw new RuntimeException("Google Maps Error: " + e.getMessage());
        }

    }


}