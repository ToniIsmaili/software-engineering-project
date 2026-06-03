package com.seeu.domains;

import com.google.gson.annotations.SerializedName;
import com.seeu.common.Responses;

import java.util.UUID;

public abstract class BaseEntity {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String isValidId(String id) {
        try {
            UUID.fromString(id);
            return null;
        } catch (Exception ignored) {
            return Responses.INVALID_ID;
        }
    }

    public abstract String validate();
}
