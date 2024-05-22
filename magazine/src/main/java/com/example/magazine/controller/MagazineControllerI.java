package com.example.magazine.controller;

import com.example.magazine.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MagazineControllerI {
    ResponseEntity<List<Product>> getAllProduct();
    ResponseEntity buyById(Long id, Double sum);
    ResponseEntity buyByName(String name, Double sum);
    ResponseEntity add(Product product);
}
