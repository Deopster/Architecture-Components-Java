package com.developersbreach.architecturecomponentsjava.detail;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developersbreach.architecturecomponentsjava.AuthDatabase;
import com.developersbreach.architecturecomponentsjava.DatabaseHelper;
import com.developersbreach.architecturecomponentsjava.MainActivity;
import com.developersbreach.architecturecomponentsjava.R;
import com.developersbreach.architecturecomponentsjava.model.Attendance;
import com.developersbreach.architecturecomponentsjava.model.Vehicle;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleDetailFragment extends Fragment {
    AuthDatabase logDB;
    public VehicleDetailFragment() {
        // Required empty public constructor
    }
    MainActivity f;
    LinearLayout add;
    DatabaseHelper database_helper;
    ArrayList<Attendance> arrayList;
    Context cont = f.getAppContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle_detail, container, false);
        final TextView vehicleNameTextView = view.findViewById(R.id.detail_vehicle_name_text_view);
        final RecyclerView recyclerV= view.findViewById(R.id.ListA);
        add = view.findViewById(R.id.AddData);
        database_helper = new DatabaseHelper(cont);
        logDB =new AuthDatabase(cont);
        Application application = Objects.requireNonNull(getActivity()).getApplication();
        Vehicle vehicle = VehicleDetailFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getDetailFragmentArgs();
        DetailFragmentViewModelFactory factory = new  DetailFragmentViewModelFactory(application, vehicle);
        DetailFragmentViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailFragmentViewModel.class);

        viewModel.getSelectedVehicle().observe(this, new Observer<Vehicle>() {
            @Override
            public void onChanged(Vehicle vehicle) {
                vehicleNameTextView.setText(vehicle.getVehicleName());
                arrayList = new ArrayList<>(database_helper.getNotes(vehicle.getVehicleName()));
                recyclerV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                NotesAdapter adapter = new NotesAdapter(cont, getActivity(), arrayList);
                recyclerV.setAdapter(adapter);

            }
        });

        return view;
    }

}
