package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.common.Utils;
import com.seeu.domains.Credentials;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthServiceImpl implements AuthService {

    @Override
    public boolean authenticate(Credentials credentials) throws Exception {
        String storedHash = null;
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.AUTHENTICATE_USER);
            ps.setString(1, credentials.getEmail());
            rs = ps.executeQuery();
            if (rs.next()) {
                storedHash = rs.getString("password_hash");
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
        if (Utils.isNullOrEmpty(storedHash)) {
            return false;
        }
        return BCrypt.checkpw(credentials.getPassword(), storedHash);
    }

    public static class SQL {
        public static final String AUTHENTICATE_USER = """
                SELECT password_hash
                FROM users
                WHERE email = ?;
                """;
    }
}
