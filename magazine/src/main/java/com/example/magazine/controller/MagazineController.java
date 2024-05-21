package com.example.magazine.controller;

import com.example.magazine.domain.Product;
import com.example.magazine.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magazineService")
public class MagazineController {
    @Autowired
    private MagazineService service;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProduct(){
        return service.getAllProduct();
    }

    @PutMapping("buyId/{id}/{sum}")
    public ResponseEntity buyId(@PathVariable("id") Long id, @PathVariable("sum") Double sum){
        return service.buyProduct(id, sum);
    }

    @PutMapping("buy/{name}/{sum}")
    public ResponseEntity buy(@PathVariable("name") String name, @PathVariable("sum") Double sum) {
        return service.buyProductByName(name, sum);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Product product){
        try {
            service.addProduct(product);
            return new ResponseEntity("product " + product + " successfully received!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("sending ERROR! (id=" + product.getID() + ")", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
