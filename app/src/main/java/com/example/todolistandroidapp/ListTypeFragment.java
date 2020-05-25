package com.example.todolistandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todolistandroidapp.Adapter.ListTypeAdapter;
import com.example.todolistandroidapp.Model.AuthModel;
import com.example.todolistandroidapp.Model.ListTypeData;
import com.example.todolistandroidapp.Model.ListTypeResponse;
import com.example.todolistandroidapp.Network.GetDataService;
import com.example.todolistandroidapp.Network.RetrofitClientInstance;
import com.example.todolistandroidapp.Service.ListTypeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTypeFragment extends Fragment {

    ListTypeAdapter adapter;
    RecyclerView listTypeRecycleView;
    List<ListTypeData> list;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_type, container, false);
         listTypeRecycleView = (RecyclerView) view.findViewById(R.id.recycleView_list_type);
        //Change action bar title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Liste Çeşitleri");
        getUserListType();
        return view;
    }

    private void getUserListType(){

        //Call add list api
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<ListTypeResponse> call = service.getUserListType(AuthModel.token);
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
                    list = response.body().getList();
                    setRecycleView();
                }
            }

            @Override
            public void onFailure(Call<ListTypeResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void setRecycleView(){
        // set up the RecyclerView
        // Lookup the recyclerview in activity layout
        if(list.size() > 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            listTypeRecycleView.setLayoutManager(layoutManager);
            listTypeRecycleView.setHasFixedSize(true);
            adapter = new ListTypeAdapter(getContext(),list ,getActivity());
            listTypeRecycleView.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listTypeRecycleView.getContext(),
                    layoutManager.getOrientation());
            listTypeRecycleView.addItemDecoration(dividerItemDecoration);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_type, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_list_type:
                addListType();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void addListType(){

        //region show add dialog
        //Show add popup

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText editTextName = new EditText(getContext());
        editTextName.setHint("Liste");
        final EditText editTextDesc = new EditText(getContext());
        editTextDesc.setHint("Açıklama");
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(editTextName);
        linearLayout.addView(editTextDesc);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());


        alert.setTitle("Yeni Liste");
        alert.setView(linearLayout);
        alert.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(editTextName.getText().toString().isEmpty() || editTextDesc.getText().toString().isEmpty()){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Liste Ekleme")
                            .setContentText("Liste ismi ve açıklama alanları zorunludur!")
                            .show();
                    return;
                }
                for(ListTypeData lst:list){
                    if(lst.getName().equals(editTextName.getText().toString().trim())){
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Liste Ekleme")
                                .setContentText(editTextName.getText().toString() + ", isimli liste zaten mevcuttur. Lütfen listenizde yer almayan bir isim giriniz")
                                .show();
                        return;
                    }
                }

                ListTypeService.addListType(getContext(),editTextName.getText().toString().trim(),editTextDesc.getText().toString().trim(),getActivity());
            }
        });

        alert.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
        //endregion
    }
}
