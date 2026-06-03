package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;

public class ScraperLog extends BaseEntity {
    @SerializedName("timestamp")
    private Instant timestamp;
    @SerializedName("message")
    private String message;
    @SerializedName("log_level")
    private String logLevel;
    public transient String scraperJobId;

    public ScraperLog() {
    }

    public ScraperLog(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setTimestamp(rs.getTimestamp("timestamp").toInstant());
        setMessage(rs.getString("message"));
        setLogLevel(rs.getString("log_level"));
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getScraperJobId() {
        return scraperJobId;
    }

    public void setScraperJobId(String scraperJobId) {
        this.scraperJobId = scraperJobId;
    }

    @Override
    public String validate() {
        return null;
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        ps.setString(1, getId());
        ps.setString(2, getMessage());
        ps.setString(3, getLogLevel());
        ps.setString(4, getScraperJobId());

    }
}
