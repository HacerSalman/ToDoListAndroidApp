package com.example.todolistandroidapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistandroidapp.Model.ListTypeData;
import com.example.todolistandroidapp.R;
import com.example.todolistandroidapp.Service.ListTypeService;

import java.util.List;

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ViewHolder> {

    private List<ListTypeData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private  Context context;
    private Activity activity;


    // data is passed into the constructor
   public ListTypeAdapter(Context context, List<ListTypeData> data, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.activity = activity;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_recycleview_list_type, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListTypeData data = mData.get(position);
        holder.title.setText(data.getName());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //Show edit popup
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                final EditText edittext = new EditText(context);
                edittext.setText(data.getName());
                final EditText edittextDesc = new EditText(context);
                edittextDesc.setText(data.getDescription());
                alert.setTitle("Listeyi düzenle");
                //TODO:
                alert.setView(edittext);
                alert.setView(edittextDesc);
                alert.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ListTypeService.editListType(context,data.getListTypeId(),edittext.getText().toString().trim(),edittextDesc.getText().toString().trim(),activity);
                    }
                });

                alert.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.

                    }
                });

                alert.show();
            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Show question popup
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                final TextView textView = new TextView(context);
                textView.setText(data.getName()+", isimli listeyi silmek istediğinizden emin misiniz?");
                alert.setTitle("Emin misiniz");
                alert.setView(textView);
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Delete the list type
                        ListTypeService.deleteListType(context,data.getListTypeId(),activity);
                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.

                    }
                });

                alert.show();

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageButton edit;
        private ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView_recycleView_list_type);
            edit = (ImageButton) itemView.findViewById(R.id.imageButton_recycleView_edit);
            delete = (ImageButton) itemView.findViewById(R.id.imageButton_recycleView_delete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ListTypeData getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
