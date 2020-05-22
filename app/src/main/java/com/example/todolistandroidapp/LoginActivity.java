package com.example.todolistandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.ListResponseModel;
import com.example.todolistandroidapp.Model.UserResponseModel;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @BindView(R.id.edittxt_login_username) EditText editTextUsername;
    @BindView(R.id.edittxt_login_password) EditText editTextPassword;
    @BindView(R.id.btn_login) Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = this.getSharedPreferences("ToDoListPref", MODE_PRIVATE);
        editor = pref.edit();
    }

    @OnClick(R.id.btn_login) void login() {

        //Check username and password
        if(editTextUsername.getText().toString().trim().isEmpty() || editTextPassword.getText().toString().trim().isEmpty() ){
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Kullanıcı Girişi")
                    .setContentText("Kullanıcı adı ve şifre boş geçilemez!")
                    .show();
            return;
        }

        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        //Call  login api service
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.login(editTextUsername.getText().toString().trim(),editTextPassword.getText().toString().trim());
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {

                //close progress dialog
                pDialog.dismissWithAnimation();

                if(response.errorBody() != null){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Hata")
                            .setContentText(response.message())
                            .show();
                }
                else if(response.body() != null){

                    editor.putString("token", response.body().getToken()); // Storing token
                    editor.commit(); // commit change

                    AuthModel.token =  response.body().getToken();
                    AuthModel.userName = response.body().getUsername();
                    AuthModel.userId = response.body().getUserId();

                    //Go to main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_sign_up) void signUp() {
        //Go to sign up activity
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
    }
}
