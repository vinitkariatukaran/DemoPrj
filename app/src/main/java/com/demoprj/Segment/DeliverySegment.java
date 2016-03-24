package com.demoprj.Segment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.demoprj.Adapter.DeliveryDetailAdapter;
import com.demoprj.AdminHomeActivity;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mounil.shah on 12/24/2015.
 */
public class DeliverySegment extends Fragment implements View.OnClickListener, TextWatcher {
    public static RecyclerView rvDeliveryDetail;
    public static RecyclerView.Adapter adapter ;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    public static LinearLayout llDeliverySearch;
    public static EditText txtDeliverySearch;
    ImageButton ibDeliverySearchClose;
    Button btnDeliverySearch;
    int searchPosition = 0;
    ArrayList<GasBooking> gasBookingList = new ArrayList<GasBooking>();
    Calendar now2 = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.segment_delivery, container, false);
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            String currentDateandTime = sdf.format(new Date());
            Date cdate= sdf.parse(currentDateandTime);
            now2= Calendar.getInstance();
            now2.add(Calendar.DATE, -1);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        rvDeliveryDetail = (RecyclerView) rootView.findViewById(R.id.rvDeliveryDetail);
        llDeliverySearch = (LinearLayout) rootView.findViewById(R.id.llDeliverySearch);
        txtDeliverySearch = (EditText) rootView.findViewById(R.id.txtDeliverySearch);
        ibDeliverySearchClose = (ImageButton) rootView.findViewById(R.id.ibDeiverySearchClose);
        btnDeliverySearch = (Button) rootView.findViewById(R.id.btnDeliverySearch);
        rvDeliveryDetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvDeliveryDetail.setLayoutManager(mLayoutManager);
        ibDeliverySearchClose.setOnClickListener(this);
        btnDeliverySearch.setOnClickListener(this);
        txtDeliverySearch.addTextChangedListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FetchingDeliveryData(now2);
    }
    public void FetchingDeliveryData(Calendar now2){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Fetching Detail");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        if(Utils.isInternetAvailable(getActivity())) {
            ParseQuery<GasBooking> gasBookingQuery = ParseQuery.getQuery(GasBooking.class);
            gasBookingQuery.whereEqualTo(AppConstant.GASBOOKING_DELIVERY_STATUS, true);
            gasBookingQuery.orderByAscending(AppConstant.GASBOOKING_DATE_OF_DELIVERY);
            gasBookingQuery.whereGreaterThanOrEqualTo(AppConstant.GASBOOKING_DATE_OF_DELIVERY, now2.getTime());
            gasBookingQuery.include(AppConstant.GASBOOKING_USER_ID);
            gasBookingQuery.include(AppConstant.GASBOOKING_GAS_SIZE_ID);
            gasBookingQuery.include(AppConstant.GASBOOKING_DELIVERYBOY_ID);
            gasBookingQuery.findInBackground(new FindCallback<GasBooking>() {
                @Override
                public void done(List<GasBooking> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            if (gasBookingList.size() > 0) {
                                gasBookingList.clear();
                            }
                            gasBookingList.addAll(objects);
                            adapter = new DeliveryDetailAdapter(getActivity(), (ArrayList<GasBooking>) objects,1);
                            rvDeliveryDetail.setAdapter(adapter);
                        }
                    } else {

                    }
                    if (progressDialog != null)
                        progressDialog.hide();
                }
            });
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDeiverySearchClose:
                txtDeliverySearch.setText("");
                llDeliverySearch.setVisibility(View.GONE);
                AdminHomeActivity.isSearchVisible = false;
                break;
            case R.id.btnDeliverySearch:
                PopupMenu pop = new PopupMenu(getActivity(),btnDeliverySearch);
                pop.getMenuInflater().inflate(R.menu.menu_search_delivery_popup, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.search_By_today:
                                try {
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    Date cdate= sdf.parse(currentDateandTime);
                                    Calendar now2= Calendar.getInstance();
                                    now2.add(Calendar.DATE, -1);
                                    FetchingDeliveryData(now2);
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.search_By_Last_7:
                                try {
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    Date cdate= sdf.parse(currentDateandTime);
                                    Calendar now2= Calendar.getInstance();
                                    now2.add(Calendar.DATE, -7);
                                    FetchingDeliveryData(now2);
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.search_By_Last_10:
                                try {
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    Date cdate= sdf.parse(currentDateandTime);
                                    Calendar now2= Calendar.getInstance();
                                    now2.add(Calendar.DATE, -10);
                                    FetchingDeliveryData(now2);
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.search_By_Last_15:
                                try {
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    Date cdate= sdf.parse(currentDateandTime);
                                    Calendar now2= Calendar.getInstance();
                                    now2.add(Calendar.DATE, -15);
                                    FetchingDeliveryData(now2);
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return false;
                    }

                });

                pop.show();//showing popup menu
                break;
        }
//        Intent next = new Intent(getActivity(), AddCustomerDetail.class);
//        startActivity(next);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ArrayList<GasBooking> searchUserList = new ArrayList<>();
        for (GasBooking d : gasBookingList) {
           String name = d.getName();
            if (name.toLowerCase().contains(txtDeliverySearch.getText().toString().toLowerCase())) {
                searchUserList.add(d);
            }
        }
        adapter = new DeliveryDetailAdapter(getActivity(), searchUserList,1);
        rvDeliveryDetail.setAdapter(adapter);
    }
}
