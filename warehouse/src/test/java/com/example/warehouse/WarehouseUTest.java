package com.example.warehouse;

import com.example.warehouse.domain.Product;
import com.example.warehouse.proxy.WarehouseMagazineProxy;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarehouseUTest {
    @Mock
    WarehouseRepository repository;

    @InjectMocks
    WarehouseService service;

    /**
     * Здесь мы можем проверить только функцию удаления.
     */
    @Test
    public void UTestPush() {
        //Предусловие............................
        Product product = new Product();
        product.setID(1L);
        product.setName("Absolute Orange");
        product.setPrice(9999999999.9);

        given(repository.findById(product.getID())).willReturn(Optional.of(product));
        Assertions.assertEquals(repository.findById(1L).get().getName(), "Absolute Orange");
        //........................................

        //Блок действия...........................
        service.pushProductOnMagazine(1L);
        //........................................

        //Блок проверки действия..................
        Assertions.assertEquals(service.getAllProduct().getBody().toString(), "[]");
        //........................................
    }
}
