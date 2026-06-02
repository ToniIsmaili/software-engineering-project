package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

public class Scraper extends BaseEntity {
    @SerializedName("status")
    private Status status;
    @SerializedName("last_run")
    private Instant lastRun;
    private transient String retailerId;

    public Scraper() {
    }

    public Scraper(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setStatus(Status.valueOf(rs.getString("status")));
        setLastRun(rs.getTimestamp("last_run").toInstant());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getLastRun() {
        return lastRun;
    }

    public void setLastRun(Instant lastRun) {
        this.lastRun = lastRun;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        if (getStatus() == null) {
            return "Invalid Scraper Status!";
        }
        return null;
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        ps.setString(1, getId());
        ps.setString(2, getStatus().name());
        ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        ps.setString(4, getRetailerId());
    }

    public enum Status {
        IDLE, RUNNING
    }
}
