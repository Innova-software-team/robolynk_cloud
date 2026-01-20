package com.example.restaurantsevices.Controllers;
import com.example.restaurantsevices.Repo.ProductRepo;
import com.example.restaurantsevices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}

