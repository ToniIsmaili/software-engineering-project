package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

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
