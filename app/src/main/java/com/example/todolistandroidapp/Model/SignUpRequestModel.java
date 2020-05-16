package com.example.todolistandroidapp.Model;

import com.google.gson.annotations.SerializedName;

public class SignUpRequestModel {

    @SerializedName("user_name")
    private String username;
    @SerializedName("full_name")
    private String fullname;
    @SerializedName("password")
    private String password;

    public SignUpRequestModel(String username, String fullname, String password) {
        this.fullname = fullname;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
