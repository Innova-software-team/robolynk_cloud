package com.example.restaurantsevices.Services;
import com.example.restaurantsevices.Repo.ProductRepo;
import com.example.restaurantsevices.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductServices {
    @Autowired
    private ProductRepo productRepo;

    public Products addProduct(Products product) {
        return productRepo.save(product);
    }
    public List<Products> getAllProducts() {
        return productRepo.findAll();
    }
    public Products getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }

}
