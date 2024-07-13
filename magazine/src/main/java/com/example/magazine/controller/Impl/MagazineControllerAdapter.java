package com.example.magazine.controller.Impl;

import com.example.magazine.controller.MagazineControllerI;
import com.example.magazine.controller.MyBasket;
import com.example.magazine.domain.Product;
import com.example.magazine.service.RequestFileGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/MS")
public class MagazineControllerAdapter implements MagazineControllerI {
    private BigDecimal calculateSum;

    private MyBasket basket = new MyBasket();

    @Autowired
    private MagazineController magazineController;

    @Autowired
    private RequestFileGateWay fileGateWay;

    @GetMapping("/list")
    @Override
    public ResponseEntity getAllProduct() {
        return magazineController.getAllProduct();
    }

    @PutMapping("/buyId/{id}/{sum}")
    @Override
    public ResponseEntity buyById(@PathVariable("id") Long id, @PathVariable("sum") Double sum) {
        ResponseEntity response = magazineController.buyById(id, sum);
        try {
            fileGateWay.reportRequest("BYU_PRODUCT.md", "buyById: " + response.toString());
            return response;
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }
    }

    @PutMapping("/buy/{name}/{sum}")
    @Override
    public ResponseEntity buyByName(@PathVariable("name") String name, @PathVariable("sum") Double sum) {
        ResponseEntity response = magazineController.buyByName(name, sum);
        try {
            fileGateWay.reportRequest("BYU_PRODUCT.md", "buyByName: " + response.toString());
            return response;
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity add(@RequestBody Product product) {
        ResponseEntity response = magazineController.add(product);
        try {
            fileGateWay.reportRequest("ADD_PRODUCT.md", "addProduct: " + response);
            return response;
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }
    }

    /**
     * Получить продукт по id
     * @param id
     * @return
     */
    @GetMapping("/prod/{id}")
    public ResponseEntity getProductDataById(@PathVariable("id") Long id) {
        List<Product> temp = magazineController.getAllProduct().getBody();
        for (Product p : temp) {
            if(p.getID().longValue() == id.longValue()){
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Добавить в корзину
     * @param name
     * @return
     */
    @PutMapping("/addFromBasket/{name}")
    public ResponseEntity addFromBasket(@PathVariable("name") String name) {
        Product product = magazineController.getAllProduct().getBody()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .get();

        ResponseEntity response;
        if (product != null) {
            basket.add(product);
            response = new ResponseEntity<>(product + "added in basket", HttpStatus.OK);
        } else {
            response = new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            fileGateWay.reportRequest("BASKET.md", "addProduct: " + response);
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }

        return response;
    }

    /**
     * Удалить из корзины
     * @param name
     * @return
     */
    @DeleteMapping("/delFromBasket/{name}")
    public ResponseEntity delFromBasket(@PathVariable("name") String name) {
        Product temp = basket.delete(name);

        ResponseEntity response;
        if(temp != null){
            response = new ResponseEntity<>(temp + "deleted in basket", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            fileGateWay.reportRequest("BASKET.md", "deleteFromBasket: " + response);
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }

        return response;
    }

    /**
     * Вывести сумму покупок
     * @return
     */
    @GetMapping("/sumOfBasket")
    public ResponseEntity sumOfBasket() {
        ResponseEntity response = new ResponseEntity<>(basket.sum(), HttpStatus.OK);

        try {
            fileGateWay.reportRequest("BASKET.md", "sumOfBasket: " + response);
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }

        return response;
    }

    /**
     * Показать содержимое корзины
     * @return
     */
    @GetMapping("/basketList")
    public ResponseEntity basketList() {
        ResponseEntity response = new ResponseEntity<>(basket.viewList(), HttpStatus.OK);

        try {
            fileGateWay.reportRequest("BASKET.md", "basketList: " + response);
        } catch (Exception e){}

        return response;
    }

    /**
     * Показать купить содержимое корзины
     * @return
     */
    @PutMapping("/payBasket/{sum}")
    public ResponseEntity payBasket(@PathVariable("sum") double sum) {
        List<Product> basketL = basket.viewList();
        double sumProducts = 0.0;
        for (Product p : basketL) {
            sumProducts += p.getPrice();
        }

        ResponseEntity response;
        if(sum >= sumProducts){
            for (Product p : basketL) {
                magazineController.buyById(p.getID(), p.getPrice());
            }
            basket.clear();

            response = new ResponseEntity<>("Basket payed! change=" + (sum - sumProducts), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Basket NOT payed!", HttpStatus.CONFLICT);
        }
        try {
            fileGateWay.reportRequest("BASKET.md", "payBasket: " + response);
        } catch (Exception e){
            return new ResponseEntity<>(response.getBody() + "(NOT_LOGGED)", response.getStatusCode());
        }


        return response;
    }

}
