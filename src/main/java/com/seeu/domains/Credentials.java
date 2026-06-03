package com.seeu.domains;

import com.google.gson.annotations.SerializedName;
import com.seeu.common.Utils;

public class Credentials {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validate() {
        if (Utils.isNullOrEmpty(email)) {
            return "{\"message\": \"Missing email: An email is required and cannot be empty.\"}";
        }
        if (Utils.isNullOrEmpty(password)) {
            return "{\"message\": \"Missing password: A password is required and cannot be empty.\"}";
        }
        return null;
    }
}
