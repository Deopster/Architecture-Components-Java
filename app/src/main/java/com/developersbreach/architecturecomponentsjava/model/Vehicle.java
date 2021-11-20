package com.developersbreach.architecturecomponentsjava.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {

    // String variable to show name of each vehicle in list
    private String mVehicleName;
    // public constructor
    public Vehicle( String vehicleName) {
        this.mVehicleName = vehicleName;
    }

    public String getVehicleName() {
        return mVehicleName;
    }

    // Methods which makes our class parcelable
    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    private Vehicle(Parcel in) {
        mVehicleName = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mVehicleName);
    }
}
