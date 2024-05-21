package com.example.warehouse.proxy;

import com.example.warehouse.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Связь магазина и склада через OpenFeign для передачи продуктов из склада в магазин
 */
@FeignClient(name = "magazine", url = "${links.magazineLink}")
public interface WarehouseMagazineProxy {
    @PostMapping("/add")
    ResponseEntity add(@RequestBody Product product);

    @GetMapping("/list")
    ResponseEntity<List<Product>> getAllProduct();
}
