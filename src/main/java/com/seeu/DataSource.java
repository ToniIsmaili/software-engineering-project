package com.seeu;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariDataSource ds;

    private DataSource() {}

    public static synchronized void init() {
        if (ds != null) return;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found", e);
        }

        String dbUrl = System.getenv("DB_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new IllegalStateException("DB_URL environment variable is not set");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);
        config.setPoolName("BotLabsPool");

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            throw new IllegalStateException("DataSource not initialized. Call DataSource.init() first.");
        }
        return ds.getConnection();
    }

    public static synchronized void close() {
        if (ds != null && !ds.isClosed()) {
            ds.close();
        }
    }
}
