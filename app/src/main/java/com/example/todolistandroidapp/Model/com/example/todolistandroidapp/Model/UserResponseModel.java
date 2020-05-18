package com.example.todolistandroidapp.Model;
import com.google.gson.annotations.SerializedName;

public class UserResponseModel extends com.example.todolistandroidapp.Model.BaseResponse {
    @SerializedName("user_name")
    private String username;
    @SerializedName("full_name")
    private String fullname;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private Byte status;
    @SerializedName("created_date")
    private long createdDate;

    public UserResponseModel(String username, String fullname, String token, long userId, Byte status, long createdDate, String message) {
        super(message);
        this.fullname = fullname;
        this.username = username;
        this.createdDate = createdDate;
        this.status = status;
        this.token = token;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}