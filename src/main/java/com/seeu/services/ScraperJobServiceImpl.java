package com.seeu.services;

import com.seeu.DataSource;
import com.seeu.common.Responses;
import com.seeu.domains.ScraperJob;
import com.seeu.domains.ScraperLog;
import jakarta.ws.rs.BadRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScraperJobServiceImpl implements ScraperJobService {

    @Override
    public List<ScraperJob> getByRetailerId(String retailerId) throws Exception {
        List<ScraperJob> scraperJobs = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_BY_RETAILER_ID);
            ps.setString(1, retailerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                scraperJobs.add(new ScraperJob(rs));
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
        return scraperJobs;
    }

    @Override
    public void startJob(String retailerId) throws Exception {
        String scraperId = getScraperId(retailerId);
        if (scraperId == null) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.START_JOB)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, scraperId);
            ps.executeUpdate();
        }
    }

    @Override
    public void endJob(String retailerId, String jobId, String status) throws Exception {
        String scraperId = getScraperId(retailerId);
        if (scraperId == null) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.END_JOB)) {
            ps.setString(1, status);
            ps.setString(2, jobId);
            ps.setString(3, scraperId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<ScraperLog> getLogs(String jobId) throws Exception {
        List<ScraperLog> scraperLogs = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_LOGS);
            ps.setString(1, jobId);
            rs = ps.executeQuery();
            while (rs.next()) {
                scraperLogs.add(new ScraperLog(rs));
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
        return scraperLogs;
    }

    @Override
    public void addLog(ScraperLog scraperLog) throws Exception {
        try (Connection connection = DataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(SQL.ADD_LOG)) {
            scraperLog.populatePs(ps);
            ps.executeUpdate();
        }
    }

    private String getScraperId(String retailerId) throws Exception {
        Connection connection = DataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL.GET_SCRAPER_ID);
            ps.setString(1, retailerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
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

    public static class SQL {
        public static final String GET_BY_RETAILER_ID = """
                SELECT *
                FROM scraper_jobs sj
                         INNER JOIN scrapers s ON s.id = sj.scraper_id
                WHERE s.retailer_id = ?::uuid;
                """;

        public static final String GET_SCRAPER_ID = """
                SELECT id
                FROM scrapers
                WHERE retailer_id = ?::uuid;
                """;

        public static final String START_JOB = """
                INSERT INTO scraper_jobs (id,
                                          start_time,
                                          end_time,
                                          status,
                                          scraper_id)
                VALUES (?::uuid,
                        NOW(),
                        NULL,
                        'RUNNING',
                        ?::uuid);
                """;

        public static final String END_JOB = """
                UPDATE scraper_jobs
                SET status   = ?,
                    end_time = NOW()
                WHERE id = ?::uuid AND scraper_id = ?::uuid;
                """;

        public static final String GET_LOGS = """
                SELECT * FROM scraper_logs
                WHERE scraper_job_id = ?::uuid;
                """;

        public static final String ADD_LOG = """
                INSERT INTO scraper_logs (id,
                                          timestamp,
                                          message,
                                          log_level,
                                          scraper_job_id)
                VALUES (?::uuid,
                        NOW(),
                        ?,
                        ?,
                        ?::uuid)
                """;
    }
}
