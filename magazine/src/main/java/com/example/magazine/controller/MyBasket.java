package com.example.magazine.controller;


import com.example.magazine.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyBasket {
    List<Product> basket;

    public MyBasket() {
        basket = new ArrayList<>();
    }

    public void add(Product p){
        basket.add(p);
    }

    public List<Product> viewList(){
        return basket;
    }

    public Product delete(String name){
        for (int i = 0; i < basket.size(); i++) {
            if(basket.get(i).getName().equals(name)){
                return basket.remove(i);
            }
        }

        return null;
    }

    public BigDecimal sum(){
        BigDecimal temp = new BigDecimal(0);
        for (Product p : basket) {
            temp = new BigDecimal(p.getPrice() + temp.doubleValue());
        }

        return temp;
    }

    public void clear(){
        basket.clear();
    }
}
