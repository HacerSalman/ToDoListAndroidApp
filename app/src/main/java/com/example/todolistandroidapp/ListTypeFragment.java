package com.example.todolistandroidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListTypeFragment extends Fragment {

    public ListTypeFragment() {
        // Required empty public constructor
    }

    public static ListTypeFragment newInstance(String param1, String param2) {
        ListTypeFragment fragment = new ListTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_type, container, false);
        return view;
    }
}
