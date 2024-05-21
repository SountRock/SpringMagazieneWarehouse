package com.example.warehouse.service;

import com.example.warehouse.aspect.ToRegistrReturnMethod;
import com.example.warehouse.aspect.ToRegistrStartMethod;
import com.example.warehouse.controller.MetricVariables;
import com.example.warehouse.domain.Product;
import com.example.warehouse.exception.InvalidLinkException;
import com.example.warehouse.proxy.WarehouseMagazineProxy;
import com.example.warehouse.repository.WarehouseRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WarehouseService {
    @Autowired
    private MetricVariables metricVariables;

    @Autowired
    private WarehouseRepository repository;

    @Autowired
    private WarehouseMagazineProxy proxy;

    /**
     * Отослать продукт в магазин по id
     * @param id
     * @return rusult of pushing
     */
    @ToRegistrStartMethod
    @ToRegistrReturnMethod
    @Transactional(rollbackOn = InvalidLinkException.class)
    public ResponseEntity pushProductOnMagazineById(Long id){
        metricVariables.getCount_try_put_request().increment();

        Product product;
        try {
            product = repository.findById(id).get();
        } catch (NoSuchElementException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return push(product);
    }

    /**
     * Отослать продукт в магазин по имени
     * @param name
     * @return rusult of pushing
     */
    @ToRegistrStartMethod
    @ToRegistrReturnMethod
    @Transactional(rollbackOn = InvalidLinkException.class)
    public ResponseEntity pushProductOnMagazineByName(String name){
        metricVariables.getCount_try_put_request().increment();

        Product temp;
        try {
            temp = repository.findByName(name).get(0);
        } catch (IndexOutOfBoundsException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return push(temp);
    }

    private ResponseEntity push(Product product){
        repository.deleteMy(product.getID()); //!!! При проблемме с выполнением добавления продукта в магазин товар не должен быть удален (можно к примеру указать неверную ссылку сервера магазина в yaml :) )

        try {
            metricVariables.getCount_put_request().increment();
            return proxy.add(product);
        } catch (FeignException e){
            throw new InvalidLinkException();
        }
    }

    /**
     * Получить список продуктов
     * @return
     */
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> temp = repository.findAll();
        if(temp != null){
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Добавить продукт
     * @param newProduct
     */
    public void addProduct(Product newProduct){
        repository.save(newProduct);
    }

    /**
     * Добавить продукты
     * @param newProducts
     */
    public void addProducts(List<Product> newProducts){
        repository.saveAll(newProducts);
    }

    public ResponseEntity<List<Product>> getMagazineProductReport(){
        return proxy.getAllProduct();
    }
}
