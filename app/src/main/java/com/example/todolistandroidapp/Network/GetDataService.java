package com.example.todolistandroidapp.Network;

import com.example.todolistandroidapp.Model.SignUpRequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<SignUpRequestModel>> getAllPhotos();
}
