package com.example.eurekaMagNWare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaMagNWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaMagNWareApplication.class, args);
	}

}
