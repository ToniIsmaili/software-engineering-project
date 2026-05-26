package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Retailer extends BaseEntity {
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Retailer() {
    }

    public Retailer(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setName(rs.getString("name"));
        setUrl(rs.getString("url"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        return null;
    }

    public void populatePs(PreparedStatement ps) throws SQLException {
        ps.setString(1, getId());
        ps.setString(2, getName());
        ps.setString(3, getUrl());
    }
}
