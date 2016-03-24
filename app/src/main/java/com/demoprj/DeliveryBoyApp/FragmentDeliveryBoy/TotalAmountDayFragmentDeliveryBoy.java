package com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demoprj.R;

import java.util.Date;

import static android.content.SharedPreferences.*;

/**
 * Created by Mounil on 24/01/2016.
 */
public class TotalAmountDayFragmentDeliveryBoy extends Fragment {

    long savedDate;
    long currentDate;
    boolean dateChanged = false;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.fragment_delivery_details, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Editor editor = pref.edit();
        savedDate = pref.getLong("Date", 0);
        currentDate = new Date().getTime();
        if(currentDate!=savedDate){
            editor.putFloat("Date", currentDate);
            dateChanged = true;
        }else{
            dateChanged = false;
        }
        return rootView;
    }
}
