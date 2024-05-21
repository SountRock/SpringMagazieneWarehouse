package com.example.magazine;

import com.example.magazine.domain.Product;
import com.example.magazine.repository.MagazineRepository;
import com.example.magazine.service.MagazineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class MagazineITest {
	@Autowired
	private MagazineService service;

	@MockBean
	private MagazineRepository repository;

	@Test
	void ITestBuy() {
		//Предусловие............................
		Product product = new Product();
		product.setID(1L);
		product.setName("Apple");
		product.setPrice(66.6);

		when(repository.findById(product.getID())).thenReturn(Optional.of(product));

		Assertions.assertEquals(repository.findById(1L).get().getName(), "Apple");
		//........................................

		//Блок действия...........................
		ResponseEntity response = service.buyProduct(1L, 70.0);
		//........................................

		//Блок проверки действия..................
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(service.getAllProduct().getBody().toString(), "[]");
		//........................................
	}

}
