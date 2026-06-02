package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.ProductPrice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPricingServiceImpl implements ProductPricingService {

    @Override
    public List<ProductPrice> getByProductId(String productId) throws Exception {
        List<ProductPrice> productPrices = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_PRODUCT_ID);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                productPrices.add(new ProductPrice(rs));
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
        return productPrices;
    }

    @Override
    public Map<String, List<ProductPrice>> getAllGroupedByProduct() throws Exception {
        Map<String, List<ProductPrice>> map = new HashMap<>();
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.GET_ALL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductPrice price = new ProductPrice(rs);

                map.computeIfAbsent(
                        price.getProductId(),
                        k -> new ArrayList<>()
                ).add(price);
            }
        }
        return map;
    }

    @Override
    public void save(ProductPrice productPrice) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.SAVE)) {
            productPrice.populatePs(ps);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String productId, String productPricingId) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.DELETE)) {
            ps.setString(1, productPricingId);
            ps.setString(2, productId);
            ps.executeUpdate();
        }
    }

    public static class SQL {
        public static final String GET_BY_PRODUCT_ID = """
                SELECT *
                FROM product_prices
                WHERE product_id = ?::uuid;
                """;

        public static final String GET_ALL = """
                SELECT *
                FROM product_prices;
                """;

        public static final String SAVE = """
                INSERT INTO product_prices (id,
                                            price,
                                            availability,
                                            last_updated,
                                            product_id,
                                            retailer_id)
                VALUES (?::uuid,
                        ?,
                        ?,
                        ?,
                        ?::uuid,
                        ?::uuid)
                ON CONFLICT (id)
                    DO UPDATE SET price        = EXCLUDED.price,
                                  availability = EXCLUDED.availability,
                                  last_updated = EXCLUDED.last_updated,
                                  product_id   = EXCLUDED.product_id,
                                  retailer_id  = EXCLUDED.retailer_id;
                """;

        public static final String DELETE = """
                DELETE FROM product_prices
                WHERE id = ?::uuid
                  AND product_id = ?::uuid;
                """;
    }
}
