package com.example.demo.repository;

import com.example.demo.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByIdIn(List<String> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id in:ids")
    List<Product> findByIdInForUpdate(@Param("ids") List<String> ids);

    List<String> id(String id);
}
