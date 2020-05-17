package com.example.todolistandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolistandroidapp.Model.SignUpRequestModel;
import com.example.todolistandroidapp.Model.SignUpResponseModel;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.btn_sign_up_ok)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @OnClick(R.id.btn_sign_up_ok) void SignUp() {
        /*Create handle for the RetrofitInstance interface*/
        SignUpRequestModel requestModel = new SignUpRequestModel("xxx","yyyy","zzzz");
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SignUpResponseModel> call = service.signUp(requestModel);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
               Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
