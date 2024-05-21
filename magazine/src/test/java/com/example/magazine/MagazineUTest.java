package com.example.magazine;

import com.example.magazine.domain.Product;
import com.example.magazine.repository.MagazineRepository;
import com.example.magazine.service.MagazineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MagazineUTest {
    @Mock
    private MagazineRepository repository;

    @InjectMocks
    private MagazineService service;

    @Test
    public void UTestBuy(){
        //Предусловие............................
        Product product = new Product();
        product.setID(1L);
        product.setName("Apple");
        product.setPrice(66.6);

        given(repository.findById(product.getID())).willReturn(Optional.of(product));

        Assertions.assertEquals(repository.findById(1L).get().getName(), "Apple"); //Проверка добавления
        //........................................

        //Блок действия...........................
        ResponseEntity response = service.buyProduct(1L, 70.0);
        //........................................

        //Блок проверки действия..................
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(service.getAllProduct().getBody().toString(), "[]"); //После покупки должно не остаться продуктов
        //........................................
    }

}
