package com.example.todolistandroidapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListResponseModel {
    @SerializedName("list")
    private ArrayList<ListData> list;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("message")
    private String message;

    public ArrayList<ListData> getList() {
        return list;
    }

    public void setList(ArrayList<ListData> list) {
        this.list = list;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}