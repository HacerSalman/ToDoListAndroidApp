package com.example.todolistandroidapp.Network;

import com.example.todolistandroidapp.Model.SignUpRequestModel;
import com.example.todolistandroidapp.Model.SignUpResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/signUp")
    Call<SignUpResponseModel> signUp(@Body SignUpRequestModel requestModel);


}
