package com.developersbreach.architecturecomponentsjava.detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developersbreach.architecturecomponentsjava.DatabaseHelper;
import com.developersbreach.architecturecomponentsjava.R;
import com.developersbreach.architecturecomponentsjava.model.Attendance;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {

    Context context;
    Activity activity;
    ArrayList<Attendance> arrayList;
    DatabaseHelper database_helper;

    public NotesAdapter(Context context,Activity activity, ArrayList<Attendance> arrayList) {
        this.context = context;
        this.activity  = activity ;
        this.arrayList = arrayList;
    }

    @Override
    public NotesAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotesAdapter.viewHolder holder, final int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.description.setText(arrayList.get(position).getDes());
        database_helper = new DatabaseHelper(context);

        holder.delete.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                //deleting note
                database_helper.delete(arrayList.get(position).getID());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView delete, edit;
        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }

}