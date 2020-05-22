package com.example.todolistandroidapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolistandroidapp.Model.ListTypeData;
import com.example.todolistandroidapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserListTypeAdapter  extends ArrayAdapter<ListTypeData> {
    private Context mContext;
    private List<ListTypeData> typeList = new ArrayList<>();

    public UserListTypeAdapter(@NonNull Context context,List<ListTypeData> list) {
        super(context, 0 , list);
        mContext = context;
        typeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.spinner_layout,parent,false);

        ListTypeData listType = typeList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textview_spinner);
        name.setText(listType.getName());

        return listItem;
    }
}
