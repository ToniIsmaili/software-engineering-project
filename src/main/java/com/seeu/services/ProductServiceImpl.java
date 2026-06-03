package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.Product;
import com.seeu.domains.ProductPrice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    @Override
    public Product get(String productId) throws Exception {
        Product product = null;
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_ID);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                 product = new Product(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            connection.close();
        }
        if (product != null) {
            product.setProductPrices(new ProductPricingServiceImpl().getByProductId(productId));
        }
        return product;
    }

    @Override
    public List<Product> getAll() throws Exception {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.GET_ALL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(rs));
            }
        }
        Map<String, List<ProductPrice>> productPricingMap = new ProductPricingServiceImpl().getAllGroupedByProduct();
        for (Product product : products) {
            product.setProductPrices(productPricingMap.get(product.getId()));
        }
        return products;
    }

    @Override
    public List<String> getCategories() throws Exception {
        List<String> categories = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL.GET_CATEGORIES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }

    @Override
    public void save(Product product) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.SAVE)) {
            product.populatePs(ps);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String productId) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.DELETE)) {
            ps.setString(1, productId);
            ps.executeUpdate();
        }
    }

    public static class SQL {
        public static final String GET_ALL = """
                SELECT *
                FROM products;
                """;

        public static final String GET_CATEGORIES = """
                SELECT DISTINCT category
                FROM products
                WHERE category IS NOT NULL
                ORDER BY category;
                """;

        public static final String GET_BY_ID = """
                SELECT *
                FROM products
                WHERE id = ?::uuid;
                """;

        public static final String SAVE = """
                INSERT INTO products (id,
                                      name,
                                      description,
                                      brand,
                                      category,
                                      specifications)
                VALUES (?::uuid,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?::text)
                ON CONFLICT (id)
                    DO UPDATE SET name           = EXCLUDED.name,
                                  description    = EXCLUDED.description,
                                  brand          = EXCLUDED.brand,
                                  category       = EXCLUDED.category,
                                  specifications = EXCLUDED.specifications;
                """;

        public static final String DELETE = """
                DELETE
                FROM products
                WHERE id = ?::uuid;
                """;
    }
}
