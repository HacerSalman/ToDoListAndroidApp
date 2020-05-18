package com.example.todolistandroidapp.Model;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("message")
    private String message;

    public BaseResponse(String message) {
       this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
