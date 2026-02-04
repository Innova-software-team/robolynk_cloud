package com.example.restaurantsevices.Services;
import com.example.restaurantsevices.Repo.ProductRepo;
import com.example.restaurantsevices.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {
    private static ProductService instance;

    @Autowired
    private ProductRepo productRepo;

    @PostConstruct
    private void initInstance() {
        instance = this;
    }

    public static ProductService getInstance() {
        return instance;
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }



    public List<Product> getProductsByRestaurantId(Long RestaurantId) {
        return productRepo.findByRestaurantId(RestaurantId);
    }

}
