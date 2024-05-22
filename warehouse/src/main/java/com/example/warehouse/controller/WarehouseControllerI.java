package com.example.warehouse.controller;

import com.example.warehouse.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface WarehouseControllerI {
    ResponseEntity<List<Product>> getAllProduct();
    ResponseEntity pushProductOnMagazineByName(String name);
    ResponseEntity pushProductOnMagazineById(Long id);
    ResponseEntity add(Product product);
    ResponseEntity<List<Product>> getMagazineProductReport();
}
