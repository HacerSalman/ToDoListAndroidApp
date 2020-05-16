package com.example.todolistandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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
                    .add(R.id.fragment, mainMenuFragmentFragment).commit();
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
