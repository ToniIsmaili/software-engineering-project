package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class Scraper extends BaseEntity {
    @SerializedName("status")
    private Status status;
    @SerializedName("last_run")
    private Instant lastRun;

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        return null;
    }

    public enum Status {
        IDLE, RUNNING
    }
}
