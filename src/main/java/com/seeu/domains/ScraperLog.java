package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class ScraperLog extends BaseEntity {
    @SerializedName("timestamp")
    private Instant timestamp;
    @SerializedName("message")
    private String message;
    @SerializedName("log_level")
    private String logLevel;

    public ScraperLog() {
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

    @Override
    public String validate() {
        return null;
    }
}
