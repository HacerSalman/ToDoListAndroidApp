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

import com.example.todolistandroidapp.Model.ListData;
import com.example.todolistandroidapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter  extends ArrayAdapter<ListData> {

    private Context mContext;
    private List<ListData> list = new ArrayList<>();

    public UserListAdapter(@NonNull Context context,  ArrayList<ListData> _list) {
        super(context, 0 , _list);
        mContext = context;
        list = _list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listview_layout,parent,false);

        ListData listData = list.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.label);
        name.setText(listData.getTitle());
        return listItem;
    }
}