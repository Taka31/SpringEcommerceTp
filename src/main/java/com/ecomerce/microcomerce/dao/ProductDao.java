package com.ecomerce.microcomerce.dao;

import com.ecomerce.microcomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    Product findById(int id);

    List<Product> findByPrixGreaterThan(int prixLimit);

    @Query("SELECT p FROM Product p WHERE p.nom LIKE %:keyword%")
    List<Product> findProductsByDescriptionContainingLetterR(String keyword);
}

