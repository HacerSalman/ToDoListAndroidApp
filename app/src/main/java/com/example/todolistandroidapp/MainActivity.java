package com.example.todolistandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.ListRequestModel;
import com.example.todolistandroidapp.Model.ListResponseModel;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            MainMenuFragment mainMenuFragmentFragment = new MainMenuFragment();
            mainMenuFragmentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, mainMenuFragmentFragment).
                    addToBackStack("MainMenuFragment").commit();
        }
    }

    public void ReplaceFragment(Fragment fragment, Bundle args, String fragmentKey)
    {
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragmentKey);
        transaction.commit();
    }

}
