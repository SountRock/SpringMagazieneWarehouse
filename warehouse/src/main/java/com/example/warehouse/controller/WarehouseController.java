package com.example.warehouse.controller;

import com.example.warehouse.domain.Product;
import com.example.warehouse.exception.InvalidLinkException;
import com.example.warehouse.proxy.WarehouseMagazineProxy;
import com.example.warehouse.service.WarehouseService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.net.ConnectException;
import java.util.List;

@RestController
@RequestMapping("/warehouseService")
public class WarehouseController {
    @Autowired
    WarehouseService service;

    @PutMapping("push/{name}")
    ResponseEntity pushProductOnMagazine(@PathVariable("name") String name){
        return service.pushProductOnMagazineByName(name);
    }

    @PutMapping("pushId/{id}")
    ResponseEntity pushProductOnMagazine(@PathVariable("id") Long id){
        return service.pushProductOnMagazineById(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProduct(){
        return service.getAllProduct();
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Product product){
        try {
            service.addProduct(product);
            return new ResponseEntity("product " + product.toString() + " successfully received!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("sending ERROR! (id=" + product.getID() + ")", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/magReport")
    public ResponseEntity<List<Product>> getMagazineProductReport(){
        return service.getMagazineProductReport();
    }

    /*
    @ExceptionHandler(RestClientException.class)
    public Response handleException(RestClientException e) {
        throw new InvalidLinkException();
    }
    */
}
