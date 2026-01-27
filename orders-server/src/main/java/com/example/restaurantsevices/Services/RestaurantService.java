package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.RestaurantRepo;
import com.example.restaurantsevices.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.PlaceType;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;
    private final GeoApiContext context;

    public RestaurantService(@Value("${google.maps.api.key}") String apikey) {
        this.context = new GeoApiContext.Builder()
                .apiKey(apikey)
                .build();
    }
    public PlacesSearchResponse getNearbyRestaurants(String address, int radiusInMeters) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        if (results.length == 0) {
            throw new Exception("Address not found");
        }
        LatLng location = results[0].geometry.location;
        return PlacesApi.nearbySearchQuery(context, location)
                .radius(radiusInMeters)
                .type(PlaceType.RESTAURANT)
                .await();
    }

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
