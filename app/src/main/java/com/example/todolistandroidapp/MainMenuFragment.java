package com.example.todolistandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.ListData;
import com.example.todolistandroidapp.Model.ListResponseModel;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainMenuFragment extends Fragment {

    SharedPreferences pref;
    AuthModel authModel;

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
        pref = this.getActivity().getSharedPreferences("ToDoListPref", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.bind(this,view);
        getUserList();
        return view;
    }

    void setListView(List<ListData> list) {

        ArrayList<String> titleList = new ArrayList<String>();
        for (ListData o:list) {
            titleList.add(o.getTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                R.layout.listview_layout, titleList);
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

    void getUserList(){
        String token = pref.getString("token", null); // getting token
        //Check token
        if(token == null){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return;
        }

        authModel = new AuthModel();
        authModel.setToken(token);

        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        //Call get user list api
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ListResponseModel> call = service.getUserList(authModel.getToken());
        call.enqueue(new Callback<ListResponseModel>() {
            @Override
            public void onResponse(Call<ListResponseModel> call, Response<ListResponseModel> response) {

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
                    setListView(response.body().getList());
                }
            }

            @Override
            public void onFailure(Call<ListResponseModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
