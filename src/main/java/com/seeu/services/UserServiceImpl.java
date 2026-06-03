package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.domains.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserServiceImpl implements UserService {

    @Override
    public User get(String userId) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_ID);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs);
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
    public User getByEmail(String email) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs);
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
    public void save(User user) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.SAVE)) {
            user.populatePs(ps);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String userId) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.DELETE)) {
            ps.setString(1, userId);
            ps.executeUpdate();
        }
    }

    public static class SQL {
        public static final String GET_BY_ID = """
                SELECT * FROM users
                WHERE id = ?::uuid;
                """;

        public static final String GET_BY_EMAIL = """
                SELECT * FROM users
                WHERE email = ?;
                """;

        public static final String SAVE = """
                INSERT INTO users (id,
                                   email,
                                   password_hash,
                                   is_verified,
                                   role)
                VALUES (?::uuid,
                        ?,
                        ?,
                        ?,
                        ?)
                ON CONFLICT (id)
                    DO UPDATE SET email         = EXCLUDED.email,
                                  password_hash = EXCLUDED.password_hash,
                                  is_verified   = EXCLUDED.is_verified,
                                  role          = EXCLUDED.role;
                """;

        public static final String DELETE = """
                DELETE FROM users
                WHERE id = ?::uuid;
                """;
    }
}
