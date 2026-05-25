package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.Retailer;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RetailerServiceImpl implements RetailerService {

    @Override
    public void save(Retailer retailer) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL.SAVE);
            retailer.populatePs(ps);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            connection.close();
        }
    }

    public static class SQL {
        public static final String SAVE = """
                INSERT INTO retailers (id, name, url)
                VALUES (?::uuid, ?, ?)
                ON CONFLICT (id)
                    DO UPDATE SET
                                  name = EXCLUDED.name,
                                  url = EXCLUDED.url;
                """;
    }
}
