package com.ecomerce.microcomerce.web.controller;

import com.ecomerce.microcomerce.dao.ProductDao;
import com.ecomerce.microcomerce.exception.ProductNotFoundException;
import com.ecomerce.microcomerce.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    private final ProductDao dao;

    @Autowired
    public ProductController(ProductDao dao) {
        this.dao = dao;
    }

    @GetMapping("/Produits")
    public List<Product> listProduct() {
        return dao.findAll();
    }

    @GetMapping("/Produits/{id}")
    public Product getProduitById(@PathVariable int id) {
        Product myProduct = dao.findById(id);
        if (Objects.isNull(myProduct)) {
            throw new ProductNotFoundException("Product not Found");
        }

        return dao.findById(id);
    }

    @GetMapping("/superieur/{prix}")
    public List<Product> getPrixSuperieur(@PathVariable int prix) {
        return dao.findByPrixGreaterThan(prix);
    }

    @PostMapping("/Produits")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product productAdded = dao.save(product);
        if (productAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productAdded.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/Produits/{id}")
    public void deleteProduct(@PathVariable long id) {
        dao.deleteById(id);
    }

    @PutMapping("/Produits")
    public void update(@RequestBody Product product) {
        dao.save(product);
    }

    @GetMapping("/filter/{chaine}")
    public List<Product> getFilterByChar(@PathVariable String chaine) {
        return dao.findProductsByDescriptionContainingLetterR(chaine);
    }
}
