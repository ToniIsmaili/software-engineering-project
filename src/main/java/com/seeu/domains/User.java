package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;

public class User extends BaseEntity {
    @SerializedName("email")
    private String email;
    @SerializedName("password_hash")
    private String passwordHash;
    @SerializedName("is_verified")
    private Boolean isVerified;
    @SerializedName("created_at")
    private Instant createdAt;
    @SerializedName("role")
    private Role role;

    public User() {
    }

    public User(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setEmail(rs.getString("email"));
        setPasswordHash(rs.getString("password_hash"));
        setVerified(rs.getBoolean("is_verified"));
        setCreatedAt(rs.getTimestamp("created_at").toInstant());
        setRole(Role.valueOf(rs.getString("role")));
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
        if (isVerified == null) {
            setVerified(false);
        }
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
        if (getRole() == null) {
            return "Invalid Role Given!";
        }
        return null;
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        ps.setString(1, getId());
        ps.setString(2, getEmail());
        ps.setString(3, getPasswordHash());
        ps.setBoolean(4, getVerified());
        ps.setString(5, getRole() != null ? getRole().name() : "USER");
    }

    public enum Role {
        USER, ADMIN
    }
}
