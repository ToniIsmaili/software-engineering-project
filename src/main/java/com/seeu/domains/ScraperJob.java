package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

public class ScraperJob extends BaseEntity {
    @SerializedName("start_time")
    private Instant startTime;
    @SerializedName("end_time")
    private Instant endTime;
    @SerializedName("status")
    private Status status;

    public ScraperJob() {
    }

    public ScraperJob(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        Timestamp startTime = rs.getTimestamp("start_time");
        setStartTime(startTime != null ? startTime.toInstant() : null);
        Timestamp endTime = rs.getTimestamp("end_time");
        setEndTime(endTime != null ? endTime.toInstant() : null);
        setStatus(Status.valueOf(rs.getString("status")));
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String validate() {
        return null;
    }

    public enum Status {
        SUCCESSFUL, FAILED, INTERRUPTED, RUNNING
    }
}
