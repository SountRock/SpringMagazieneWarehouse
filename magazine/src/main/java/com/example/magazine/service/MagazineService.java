package com.example.magazine.service;

import com.example.magazine.aspect.ToRegistrReturnMethod;
import com.example.magazine.aspect.ToRegistrStartMethod;
import com.example.magazine.controller.MetricVariables;
import com.example.magazine.domain.Product;
import com.example.magazine.exception.InsufficientFundsException;
import com.example.magazine.repository.MagazineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MagazineService {
    @Autowired
    private MetricVariables metricVariables;

    @Autowired
    private MagazineRepository repository;

    /**
     * Получить список продуктов
     * @return products
     */
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> temp = repository.findAll();
        if(temp != null){
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Купить продукт по Id
     * @param id
     * @param paySum
     * @return response
     */
    @ToRegistrStartMethod
    @ToRegistrReturnMethod
    @Transactional(rollbackOn = InsufficientFundsException.class)
    public ResponseEntity buyProduct(Long id, Double paySum){
        metricVariables.getCount_try_put_request().increment();
        Product temp;
        try {
            temp = repository.findById(id).get();
        } catch (NoSuchElementException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return buy(temp, paySum);
    }

    /**
     * Купить продукт по имени
     * @param name
     * @param paySum
     * @return response
     */
    @ToRegistrStartMethod
    @ToRegistrReturnMethod
    @Transactional(rollbackOn = InsufficientFundsException.class)
    public ResponseEntity buyProductByName(String name, Double paySum) {
        metricVariables.getCount_try_put_request().increment();

        Product temp;
        try {
            temp = repository.findByName(name).get(0);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return buy(temp, paySum);
    }

    private ResponseEntity buy(Product product, Double paySum){
        repository.deleteMy(product.getID());

        double delivery = paySum - product.getPrice();
        if(delivery < 0){
            throw new InsufficientFundsException();
        }

        metricVariables.getCount_put_request().increment();

        return new ResponseEntity<>("Bought " + product + " delivery is " + delivery, HttpStatus.OK);
    }

    /**
     * Добавить продукт
     * @param newProduct
     */
    public void addProduct(Product newProduct){
        //Для случая когда продукт с таким индексом уже есть.
        Product temp = new Product();
        temp.setName(newProduct.getName());
        temp.setPrice(newProduct.getPrice());

        repository.save(temp);
    }

    /**
     * Добавить продукты
     * @param newProducts
     */
    public void addProducts(List<Product> newProducts){
        //Для случев когда продукты с такими индексами уже есть.
        for (Product p : newProducts) {
            addProduct(p);
        }
    }


}
