package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

public class Retailer extends BaseEntity {
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Retailer() {
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
}
