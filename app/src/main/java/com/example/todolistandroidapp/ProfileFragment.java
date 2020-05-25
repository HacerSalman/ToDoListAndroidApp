package com.example.todolistandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistandroidapp.Helper.Utils;
import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.BaseResponse;
import com.example.todolistandroidapp.Model.SignUpRequestModel;
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

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {


    @BindView(R.id.textView_profile_created_time)
    TextView createdTime;
    @BindView(R.id.textView_profile_username)
    TextView username;
    @BindView(R.id.editText_profile_full_name)
    EditText fullName;
    @BindView(R.id.imageButton_profile_save)
    ImageButton save;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pref = getActivity().getSharedPreferences("ToDoListPref", MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        //Change action bar title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profilim");
        getProfileInfo();
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.update_profile:
                saveProfile();
                return true;
            case R.id.logout_profile:
                logout();
                return true;
            case R.id.delete_profile:
                deleteProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void getProfileInfo(){
        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call =  service.getUser(AuthModel.token);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {

                //close progress dialog
                pDialog.dismissWithAnimation();

                if(response.errorBody() != null){
                    if(response.code() == 401){
                        //show dialog
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Zaman aşımı")
                                .setContentText("Oturumunuz zaman aşımına uğramıştır, lütfen tekrardan giriş yapınız")
                                .show();

                        //Start login activity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Hata")
                                .setContentText(response.message())
                                .show();
                    }
                }
                else if(response.body() != null){
                 username.setText(response.body().getUsername());
                 fullName.setText(response.body().getFullname());
                 createdTime.setText(Utils.formatDate(Utils.unixTimeToDate(response.body().getCreatedDate())));
                 AuthModel.userId = response.body().getUserId();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.imageButton_profile_save) void save() {
        saveProfile();
    }


    void saveProfile(){
        //Call update user
        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        SignUpRequestModel requestModel = new SignUpRequestModel(username.getText().toString(),fullName.getText().toString().trim(),null);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call =  service.updateUser(AuthModel.token,requestModel);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {

                //close progress dialog
                pDialog.dismissWithAnimation();

                if(response.errorBody() != null){
                    if(response.code() == 401){
                        //show dialog
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Zaman aşımı")
                                .setContentText("Oturumunuz zaman aşımına uğramıştır, lütfen tekrardan giriş yapınız")
                                .show();

                        //Start login activity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Hata")
                                .setContentText(response.message())
                                .show();
                    }
                }
                else if(response.body() != null){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("İşlem Başarılı")
                            .setContentText("İşleminiz başarıyla gerçekleşmiştir")
                            .show();
                    username.setText(response.body().getUsername());
                    fullName.setText(response.body().getFullname());
                    createdTime.setText(Utils.formatDate(Utils.unixTimeToDate(response.body().getCreatedDate())));
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void logout(){

        editor.putString("token", ""); // Storing token
        editor.commit(); // commit change

        AuthModel.token =  "";
        AuthModel.userName = "";
        AuthModel.userId = 0;
        //Start login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    void deleteProfile(){

        //Show warning dialog for delete user account
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Emin misiniz?")
                .setContentText("Hesabınızı silmek istediğinize emin misiniz?")
                .setCancelText("Hayır")
                .setConfirmText("Evet")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        return;
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        deleteUserAccount();
                    }
                })
                .show();
    }

    void deleteUserAccount(){
        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<BaseResponse> call =  service.deleteUser(AuthModel.token,AuthModel.userId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                //close progress dialog
                pDialog.dismissWithAnimation();

                if(response.errorBody() != null){
                    if(response.code() == 401){
                        //show dialog
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Zaman aşımı")
                                .setContentText("Oturumunuz zaman aşımına uğramıştır, lütfen tekrardan giriş yapınız")
                                .show();

                        //Start login activity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Hata")
                                .setContentText(response.message())
                                .show();
                    }
                }
                else if(response.body() != null){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("İşlem Başarılı")
                            .setContentText("İşleminiz başarıyla gerçekleşmiştir")
                            .show();
                    logout();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

