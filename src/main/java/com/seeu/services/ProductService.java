package com.seeu.services;

import com.seeu.domains.Product;

import java.util.List;

public interface ProductService {
    Product get(String productId) throws Exception;

    List<Product> getAll() throws Exception;

    List<String> getCategories() throws Exception;

    void save(Product product) throws Exception;

    void delete(String productId) throws Exception;
}
