package com.example.todolistandroidapp.Network;

import com.example.todolistandroidapp.Model.SignUpRequestModel;
import com.example.todolistandroidapp.Model.SignUpResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {

    @POST("/user/SignUp")
    Call<SignUpResponseModel> signUp(@Body SignUpRequestModel requestModel);


}
