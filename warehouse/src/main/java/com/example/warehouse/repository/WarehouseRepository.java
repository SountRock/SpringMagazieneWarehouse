package com.example.warehouse.repository;

import com.example.warehouse.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.id=:id")
    void deleteMy(Long id);
}
