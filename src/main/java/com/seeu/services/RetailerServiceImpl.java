package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.Retailer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RetailerServiceImpl implements RetailerService {

    @Override
    public List<Retailer> getAll() throws Exception {
        List<Retailer> retailers = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_ALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                retailers.add(new Retailer(rs));
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
        return retailers;
    }

    @Override
    public Retailer get(String id) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Retailer(rs);
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

    @Override
    public void delete(String id) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL.DELETE);
            ps.setString(1, id);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            connection.close();
        }
    }

    public static class SQL {
        public static final String GET_ALL = """
                SELECT * FROM retailers;
                """;

        public static final String GET_BY_ID = """
                SELECT * FROM retailers
                WHERE id = ?::uuid;
                """;

        public static final String SAVE = """
                INSERT INTO retailers (id, name, url)
                VALUES (?::uuid, ?, ?)
                ON CONFLICT (id)
                    DO UPDATE SET
                                  name = EXCLUDED.name,
                                  url = EXCLUDED.url;
                """;

        public static final String DELETE = """
                DELETE FROM retailers
                WHERE id = ?::uuid;
                """;
    }
}
