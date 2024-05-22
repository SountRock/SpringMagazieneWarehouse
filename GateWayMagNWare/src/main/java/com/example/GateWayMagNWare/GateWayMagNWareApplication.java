package com.example.GateWayMagNWare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateWayMagNWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateWayMagNWareApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("magazine",r->r.path("/magazineService/**")
						.uri("http://localhost:8080/"))
				.route("warehouse",r->r.path("/warehouseService/**")
						.uri("http://localhost:8090/"))
				.route("magazineAdvance",r->r.path("/MS/**")
						.uri("http://localhost:8080/")).build();
	}

}
