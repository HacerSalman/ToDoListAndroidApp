package com.example.todolistandroidapp;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todolistandroidapp.Adapter.UserListTypeAdapter;
import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.BaseResponse;
import com.example.todolistandroidapp.Model.ListData;
import com.example.todolistandroidapp.Model.ListRequestModel;
import com.example.todolistandroidapp.Model.ListTypeData;
import com.example.todolistandroidapp.Model.ListTypeResponse;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManageListFragment extends Fragment {

    @BindView(R.id.editText_manageList_title)
    EditText title;
    @BindView(R.id.editText_manageList_desc)
    EditText desc;
    @BindView(R.id.imageButton_manageList_save)
    ImageButton save;
    @BindView(R.id.spinner_manageList_type)
    Spinner spinner;

    ListData listData;
    AuthModel authModel;
    List<ListTypeData> listTypeData;
    int listType;


    public ManageListFragment() {
        // Required empty public constructor
    }

    public static ManageListFragment newInstance(String param1, String param2) {
        ManageListFragment fragment = new ManageListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authModel = new AuthModel();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_manage_list, container, false);

        //Change action bar title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Görev");

        //Bind butterknife
        ButterKnife.bind(this,view);

        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                ListData _listData = (ListData)bundle.getSerializable("listData");
                listData = _listData;
                if(_listData != null){
                    title.setText(_listData.getTitle());
                    desc.setText(_listData.getDescription());
                }
            }
        }
        getListType();
        return view;
    }


    //Click save button
    @OnClick(R.id.imageButton_manageList_save) void save() {
        //Call update list api method
        saveListData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manage_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_item:
                deleteeListData();
                return true;
            case R.id.save_item:
                saveListData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Call update list api method
    void saveListData(){

        ListRequestModel requestModel = new ListRequestModel();
        requestModel.setDescription(desc.getText().toString());
        requestModel.setTitle(title.getText().toString());
        requestModel.setModifierBy(AuthModel.userName);
        requestModel.setType(listType);
        //Call add list api
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<BaseResponse> call = null;
        if(listData == null){
            call = service.addList(AuthModel.token,requestModel);
        }
        else{
            requestModel.setListId(listData.getListId());
            call = service.updateList(AuthModel.token,requestModel);
        }

        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

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
                            .setContentText("İşleminiz başarılı bir şekilde gerçekleşmiştir.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Call delete list api method
    void deleteeListData(){
        ListRequestModel requestModel = new ListRequestModel();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<BaseResponse> call =  service.deleteList(AuthModel.token, listData.getListId());

        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

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
                            .setContentText("İşleminiz başarılı bir şekilde geröekleşmiştir.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getListType(){

        ListRequestModel requestModel = new ListRequestModel();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ListTypeResponse> call =  service.getUserListType(AuthModel.token);

        //Show progress dialog
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("İşlem yapılıyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<ListTypeResponse>() {
            @Override
            public void onResponse(Call<ListTypeResponse> call, Response<ListTypeResponse> response) {

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
                    listTypeData = response.body().getList();
                    List<String> list =  new ArrayList<>();
                    for ( ListTypeData data:    response.body().getList()  ) {
                        list.add(data.getName());
                    }
                    if(listData != null){
                        list.remove(listData.getTypeName());
                        list.add(0,listData.getTypeName());
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, list);
                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);
                }
            }
            @Override
            public void onFailure(Call<ListTypeResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnItemSelected(R.id.spinner_manageList_type)
    public void spinnerItemSelected(Spinner spinner, int position) {
        String selected_val=spinner.getSelectedItem().toString();
        for(ListTypeData data: listTypeData){
            if(data.getName().equals(selected_val))
                listType = data.getListTypeId();
        }
    }
}
