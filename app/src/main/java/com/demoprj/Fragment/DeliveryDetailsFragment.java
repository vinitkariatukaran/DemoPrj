package com.demoprj.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;

import com.demoprj.ParseModel.DeliveryModel;
import com.demoprj.R;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.demoprj.View.SegmentedGroup;

import java.util.ArrayList;

public class DeliveryDetailsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    SegmentedGroup DeliverySegment;
    Fragment fragment;
    public static ArrayList<DeliveryModel> DeliveryList = new ArrayList<>();
    public static boolean isDelivered = false;
    ViewGroup mainContainer;
    InputMethodManager imm;

    @Override
    public void onStart() {
        super.onStart();
        if(mainContainer!=null && imm!=null)
            imm.hideSoftInputFromWindow(mainContainer.getWindowToken(), 0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.fragment_delivery_details, container, false);
        mainContainer = container;
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        DeliverySegment = (SegmentedGroup) rootView.findViewById(R.id.DeliverySegment);
        fragment=new UndeliverySegment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_delivery, fragment);
        fragmentTransaction.commit();
        DeliverySegment.setOnCheckedChangeListener(this);
        DeliverySegment.check(R.id.rbUndeliver);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbUndeliver:
                isDelivered = false;
                fragment=new UndeliverySegment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_delivery, fragment);
                fragmentTransaction.commit();
                //  communicatorCarActivity.startCarFragment(fragment,getActivity().getString(R.string.title_car));
                break;
            case R.id.rbDelivery:
                isDelivered = true;
                fragment=new DeliverySegment();
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.container_delivery, fragment);
                fragmentTransaction1.commit();
                // communicatorCarActivity.startCarFragment(fragment, getActivity().getString(R.string.title_car));
                break;
            default:
                break;
        }
    }
}
