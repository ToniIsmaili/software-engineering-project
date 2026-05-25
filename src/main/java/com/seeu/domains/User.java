package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class User extends BaseEntity {
    @SerializedName("email")
    private String email;
    @SerializedName("passwordHash")
    private String passwordHash;
    @SerializedName("isVerified")
    private Boolean isVerified;
    @SerializedName("created_at")
    private Instant createdAt;
    @SerializedName("role")
    private Role role;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        return null;
    }

    public enum Role {
        USER, ADMIN
    }
}
