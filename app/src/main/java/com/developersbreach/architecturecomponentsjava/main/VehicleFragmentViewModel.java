package com.developersbreach.architecturecomponentsjava.main;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.developersbreach.architecturecomponentsjava.MainActivity;
import com.developersbreach.architecturecomponentsjava.MyDatabaseHelper;
import com.developersbreach.architecturecomponentsjava.model.Vehicle;


import java.util.ArrayList;
import java.util.List;

public class VehicleFragmentViewModel extends ViewModel {
    MyDatabaseHelper myDB;
    MainActivity f;
    private MutableLiveData<List<Vehicle>> mVehicleData;

    // Get vehicle list of data return type MutableLiveData
    MutableLiveData<List<Vehicle>> getVehicleData() {
        mVehicleData = new MutableLiveData<List<Vehicle>>();
        loadAllVehicles();
        return mVehicleData;
    }

    /*
     * Hardcoded vehicle data which shows in our list.
     *
     * We can use AsyncTask to load this data in background but since this is not heavy process
     * loading from database or internet.
     */
    private void loadAllVehicles() {
        Context cont = f.getAppContext();
        myDB = new MyDatabaseHelper(cont);
        List<Vehicle> vehicleList = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(cont, "list is empty", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                vehicleList.add(new Vehicle(cursor.getString(1)));
            }
        }
        //vehicleList.add(new Vehicle("Андрей Ефимов"));
        mVehicleData.postValue(vehicleList);
    }
}
