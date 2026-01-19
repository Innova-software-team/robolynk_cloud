package com.example.restaurantsevices.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restaurantsevices.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
}
