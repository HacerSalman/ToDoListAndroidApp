package com.example.todolistandroidapp.Network;

import com.example.todolistandroidapp.Model.SignUpRequestModel;
import com.example.todolistandroidapp.Model.SignUpResponseModel;
import  com.example.todolistandroidapp.Model.UserResponseModel;
import com.example.todolistandroidapp.Model.BaseResponse;
import com.example.todolistandroidapp.Model.ListRequestModel;
import com.example.todolistandroidapp.Model.ListResponseModel;
import com.example.todolistandroidapp.Model.ListTypeRequestModel;
import com.example.todolistandroidapp.Model.ListTypeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetDataService {

    //region User
    @POST("/user/SignUp")
    Call<SignUpResponseModel> signUp(@Body SignUpRequestModel requestModel);

    @GET("/user/Login")
    Call<UserResponseModel> login(@Query("username") String username,
                                  @Query("password") String password);

    @DELETE("/user/DeleteUser")
    Call<BaseResponse> deleteUser(@Header("Authorization") String credentials,
                                  @Query("userId") long userId);

    @PUT("user/UpdateUser")
    Call<UserResponseModel> updateUser(@Header("Authorization") String credentials,
                                       @Body SignUpRequestModel requestModel);

    @GET("/user/GetUser")
    Call<UserResponseModel> getUser(@Header("Authorization") String credentials);

    //endregion

    //region List
    @POST("/list/AddList")
    Call<BaseResponse> addList(@Header("Authorization") String credentials,
                               @Body ListRequestModel requestModel);

    @PUT("/list/UpdateList")
    Call<BaseResponse> updateList(@Header("Authorization") String credentials,
                                  @Body ListRequestModel requestModel);

    @POST("/list/AddMultipleList")
    Call<ListResponseModel> addMultipleList(@Header("Authorization") String credentials,
                                            @Body List<ListRequestModel> requestModel);

    @PUT("/list/AddTypeToList")
    Call<ListResponseModel> addTypeToList(@Header("Authorization") String credentials,
                                          @Query("listId") long listId,
                                          @Query("typeId") int typeId);

    @DELETE("/list/DeleteList")
    Call<BaseResponse> deleteList(@Header("Authorization") String credentials,
                                  @Query("listId") long listId);

    @GET("/list/GetUserList")
    Call<ListResponseModel> getUserList(@Header("Authorization") String credentials);
    //endregion

    //region list type
    @GET("/ListType/GetUserListType")
    Call<ListTypeResponse> getUserListType(@Header("Authorization") String credentials);

    @POST("/ListType/AddListType")
    Call<BaseResponse> updateList(@Header("Authorization") String credentials,
                                  @Body ListTypeRequestModel requestModel);

    @PUT("/ListType/UpdateListType")
    Call<BaseResponse> updateListType(@Header("Authorization") String credentials,
                                      @Body ListTypeRequestModel requestModel);

    @DELETE("/ListType/DeleteListType")
    Call<BaseResponse> deleteListType(@Header("Authorization") String credentials,
                                      @Query("typeId") long typeId);
    //endregion

}
