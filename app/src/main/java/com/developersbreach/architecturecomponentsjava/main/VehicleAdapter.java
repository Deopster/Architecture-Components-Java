package com.developersbreach.architecturecomponentsjava.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.architecturecomponentsjava.AuthDatabase;
import com.developersbreach.architecturecomponentsjava.MainActivity;
import com.developersbreach.architecturecomponentsjava.MyDatabaseHelper;
import com.developersbreach.architecturecomponentsjava.R;
import com.developersbreach.architecturecomponentsjava.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private Context mContext;
    private List<Vehicle> mVehicleList;
    private VehicleAdapterListener mListener;
    ImageView im;

    MyDatabaseHelper myDB;
    AuthDatabase logDB;
    VehicleAdapter(Context context, List<Vehicle> vehicleList, VehicleAdapterListener listener) {
        this.mContext = context;
        this.mVehicleList = vehicleList;
        this.mListener = listener;
    }

    public interface VehicleAdapterListener {
        void onVehicleSelected(Vehicle vehicle, View view);
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder {

        private TextView mVehicleNameTextView;
        ImageView delete;

        VehicleViewHolder(@NonNull final View itemView) {
            super(itemView);
            mVehicleNameTextView = itemView.findViewById(R.id.vehicle_name_text_item_view);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            logDB =new AuthDatabase(mContext);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onVehicleSelected(mVehicleList.get(getAdapterPosition()), itemView);
                }
            });
        }
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, final int position) {
        myDB = new MyDatabaseHelper(this.mContext);
        holder.mVehicleNameTextView.setText(mVehicleList.get(position).getVehicleName());
        holder.delete.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                //deleting note
                    myDB.deleteOneRow(mVehicleList.get(position).getVehicleName());
                    mVehicleList.remove(position);
                    notifyDataSetChanged();
            }
        });
        Vehicle vehicle = mVehicleList.get(position);
        holder.mVehicleNameTextView.setText(vehicle.getVehicleName());

    }
    public void updatedata(){
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }
}
