package com.example.todolistandroidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainMenuFragment extends Fragment {

    @BindView(R.id.listView_main_menu) ListView listView;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.bind(this,view);
        setListView();
        return view;
    }

    void setListView() {

        // Array of strings...
        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < mobileArray.length; ++i) {
            list.add(mobileArray[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                R.layout.listview_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                replaceFragment();
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
                }
        });
    }

    void replaceFragment() {
        Bundle args = new Bundle();
        ((MainActivity) getActivity()).ReplaceFragment(new ManageListFragment(), args,"ManageListFragment");
    }
}
