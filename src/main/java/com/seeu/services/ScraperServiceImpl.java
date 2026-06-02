package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.Scraper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ScraperServiceImpl implements ScraperService {

    @Override
    public Scraper get(String retailerId) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_ID);
            ps.setString(1, retailerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Scraper(rs);
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
    public void save(Scraper scraper) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.SAVE)) {
            scraper.populatePs(ps);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String retailerId) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.DELETE)) {
            ps.setString(1, retailerId);
            ps.executeUpdate();
        }
    }

    public static class SQL {
        public static final String GET_BY_ID = """
                SELECT *
                FROM scrapers
                WHERE retailer_id = ?::uuid;
                """;

        public static final String SAVE = """
                INSERT INTO scrapers (id,
                                      status,
                                      last_run,
                                      retailer_id)
                VALUES (?::uuid,
                        ?,
                        ?,
                        ?::uuid)
                ON CONFLICT (retailer_id)
                    DO UPDATE SET status   = EXCLUDED.status,
                                  last_run = EXCLUDED.last_run;
                """;

        public static final String DELETE = """
                DELETE FROM scrapers
                WHERE retailer_id = ?::uuid;
                """;
    }
}
