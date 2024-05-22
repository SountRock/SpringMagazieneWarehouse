package com.example.warehouse;

import com.example.warehouse.domain.Product;
import com.example.warehouse.proxy.WarehouseMagazineProxy;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class WarehouseITest {
	@Autowired
	private WarehouseService service;

	@MockBean
	private WarehouseRepository repository;

	/**
	 * Поскольку в интеграционном тесте мы должны проверить процедуру отправики продукта в магазин полностью, то требуется перед началом теста включить ConfigServer EurekaServer и Magazine, чтобы при выполнении метода Warehouse смог достучаться до магазина. (!!!)
	 */
	@Test
	void ITestPush() {
		//Предусловие............................
		Product product = new Product();
		product.setID(1L);
		product.setName("Absolute Orange");
		product.setPrice(9999999999.9);

		when(repository.findById(product.getID())).thenReturn(Optional.of(product));
		Assertions.assertEquals(repository.findById(1L).get().getName(), "Absolute Orange");
		//........................................

		//Блок действия...........................
		ResponseEntity response = service.pushProductOnMagazineById(1L);
		//........................................

		//Блок проверки действия..................
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK); //(!!!) Проверка ответа
		Assertions.assertEquals(service.getAllProduct().getBody().toString(), "[]");

		List<Product> report = service.getMagazineProductReport().getBody(); //(!!!) Проверка отсчета
		Assertions.assertEquals(report.get(report.size() - 1).getName(), "Absolute Orange");
		//........................................
	}

}
