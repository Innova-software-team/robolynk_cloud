package com.example.restaurantsevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import com.example.restaurantsevices.Services.ProductService;
import com.example.restaurantsevices.Services.RestaurantService;
import com.example.restaurantsevices.Services.UserService;
import com.example.restaurantsevices.model.Product;
import com.example.restaurantsevices.model.Restaurant;
import com.example.restaurantsevices.model.User;

@SpringBootApplication
public class RestaurantSevicesApplication implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired 
    private RestaurantService restaurantService;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantSevicesApplication.class, args);
    }

    private void createDummyData() {
        System.out.println("Creating dummy data...");

        // Create dummy user
        // user subject = 941e73d9f480fc0d9aed4f0308fb5bc5d6be0997847a75bd895d9a88b581ad9f
        System.out.println("Creating dummy user...");
        User user = userService.createUser("941e73d9f480fc0d9aed4f0308fb5bc5d6be0997847a75bd895d9a88b581ad9f");

        // Create dummy restaurant
        System.out.println("Creating dummy restaurant...");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Dummy Restaurant");
        restaurant.setAddress("123 Main St, Anytown, Somewhere");
        restaurant.setLatitude(0);
        restaurant.setLongitude(0);
    
        restaurant = restaurantService.addRestaurant(restaurant);

        // Create dummpy products
        System.out.println("Creating dummy product...");
        Product product1 = new Product();
        product1.setProduct_name("Dummy Pizza");
        product1.setRestaurant(restaurant);
        product1.setProduct_price(999l);
        
        product1 = productService.addProduct(product1);

        // Add a product to dummy user's cart
        System.out.println("Adding product to user's cart...");
        user.getCurrentCart().addProduct(product1);
        user = userService.save(user);

        // Log created data
        System.out.println("Created dummy user with ID: " + user.getId());
        System.out.println("Created dummy restaurant with ID: " + restaurant.getId());
        System.out.println("Created dummy product with ID: " + product1.getId());
    }

    @Override
    public void run(String... args) {
        createDummyData();
    }

}
