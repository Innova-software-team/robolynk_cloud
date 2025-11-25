package com.example.restaurantsevices.Controllers;
import com.example.restaurantsevices.Repo.ProductRepo;
import com.example.restaurantsevices.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @PostMapping
    public Products addProduct(@RequestBody Products products) {
        return productRepo.save(products);
    }

    @GetMapping
    public List<Products> getAllProducts() {
        return productRepo.findAll();
    }
}

