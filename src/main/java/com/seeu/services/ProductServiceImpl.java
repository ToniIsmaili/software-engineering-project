package com.seeu.services;

import com.seeu.domains.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public Product get(String productId) throws Exception {
        return null;
    }

    @Override
    public List<Product> getAll() throws Exception {
        return null;
    }

    @Override
    public void save(Product product) throws Exception {
    }

    @Override
    public void delete(String productId) throws Exception {
    }

    public static class SQL {
        public static final String GET_ALL = """
                """;

        public static final String GET_BY_ID = """
                """;

        public static final String SAVE = """
                """;

        public static final String DELETE = """
                """;
    }
}
