package com.example.restaurantsevices.Services;
import com.example.restaurantsevices.Repo.ProductRepo;
import com.example.restaurantsevices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

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
