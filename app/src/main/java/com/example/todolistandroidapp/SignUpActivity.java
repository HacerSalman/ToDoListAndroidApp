package com.example.todolistandroidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todolistandroidapp.Helper.AlertDialogUtils;
import com.example.todolistandroidapp.Model.SignUpRequestModel;
import com.example.todolistandroidapp.Model.SignUpResponseModel;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.btn_sign_up_ok)
    Button btnSignUp;

    @BindView(R.id.edittxt_sign_up_full_name)
    EditText editTextFullName;
    @BindView(R.id.edittxt_sign_up_username)
    EditText editTextUserName;
    @BindView(R.id.edittxt_sign_up_password)
    EditText editTextPassword;
    @BindView(R.id.edittxt_sign_up_rewrite_password)
    EditText editTextRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up_ok) void SignUp() {
        //Check password
        if(!editTextPassword.getText().toString().trim().equals(editTextRePassword.getText().toString().trim())){
            new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Şifreler uyuşmuyor")
                    .setContentText("Yazdığınız şifreler uyuşmuyor, lütfen tekrar deneyiniz")
                    .show();
            return;
        }

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        SignUpRequestModel requestModel = new SignUpRequestModel(editTextUserName.getText().toString(),editTextFullName.getText().toString(),editTextPassword.getText().toString());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SignUpResponseModel> call = service.signUp(requestModel);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
                pDialog.dismissWithAnimation();
                if(response.errorBody() != null){
                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Hata")
                            .setContentText(response.message())
                            .show();
                }
                else if(response.body() != null){
                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Kayıt başarılı")
                            .setContentText("Kaydınız başarıyla alınmıştır, şimdi uygulamaya giriş yapabilirsiniz")
                            .setConfirmText("Tamam")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    //Go to the login activity
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
