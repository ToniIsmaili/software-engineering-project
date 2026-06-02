package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public Product get(String productId) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_ID);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs);
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
        return null;
    }

    @Override
    public List<Product> getAll() throws Exception {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.GET_ALL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(rs));
            }
        }
        return products;
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
