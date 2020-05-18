package com.example.todolistandroidapp.Model;

import com.google.gson.annotations.SerializedName;

public class SignUpResponseModel {

    @SerializedName("userName")
    private String username;
    @SerializedName("fullName")
    private String fullname;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private long id;
    @SerializedName("createdDate")
    private long createdDate;
    @SerializedName("updatedDate")
    private long updatedDate;
    @SerializedName("ownerBy")
    private String ownerBy;
    @SerializedName("modifierBy")
    private String modifierBy;
    @SerializedName("status")
    private Byte status;

    public SignUpResponseModel(String username, String fullname, String password) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getOwnerBy() {
        return ownerBy;
    }

    public void setOwnerBy(String ownerBy) {
        this.ownerBy = ownerBy;
    }

    public String getModifierBy() {
        return modifierBy;
    }

    public void setModifierBy(String modifierBy) {
        this.modifierBy = modifierBy;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
