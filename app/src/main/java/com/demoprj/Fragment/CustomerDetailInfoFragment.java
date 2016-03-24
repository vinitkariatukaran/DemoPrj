package com.demoprj.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.demoprj.Adapter.CustomerDetailAdapter;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.GsonModel.PACK.CustomerDetailModle;
import com.demoprj.GsonModel.PACK.GasSizeDetailModel;
import com.demoprj.MapActivity;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.GPSTracker;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerDetailInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    static EditText txtParticularCustomerId;
    static EditText txtParticularCustomerName;
    static EditText txtParticularCustomerAddress;
    static EditText txtParticularCustomerGasType;
    static EditText txtParticularCustomerPhoneNumber;
    EditText txtParticularCustomerFirstName;
    EditText txtParticularCustomerMiddleName;
    EditText txtParticularCustomerLastName;
    EditText txtParticularCustomerLatLng;
    static EditText txtParticularCustomerLandmark;
    static EditText txtParticularCustomerDOB;
    static EditText txtParticularCustomerEmailId;
    static EditText txtParticularCustomerSecondaryPhoneNumber;
    static EditText txtParticularCustomerGasSize;
    static EditText txtParticularCustomerDeliveryCharge;

    static LinearLayout llParticularFirstName;
    static LinearLayout llParticularMiddleName;
    static LinearLayout llParticularLastName;
    ScrollView svParticularCustomerDetail;

    ImageButton ibClear;
    static ImageButton ibChangeDate;
    static Spinner spnParticularCustomerGasType;
    static Spinner spnParticularCustomerGasSize;

    static TextView lblCName;
    static Button btnParticularSave;
    Button btnDirection;
    String gasObjectId;
    int pos = -1;
//    FloatingActionButton fabEditCustomerDetail;
    GPSTracker gps;
    double currentLatitude;
    double currentLongitude;
    int year;
    int month;
    int day;
    Calendar c;
    RequestQueue queue;
    ArrayList<GasSizeDetailModel> gasSizeDetailModel = new ArrayList<>();
    String DOB;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.content_particular_customer_detail_info, container, false);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        btnParticularSave = (Button) rootView.findViewById(R.id.btnParticularSave);
        btnDirection = (Button) rootView.findViewById(R.id.btnDirection);
        ibClear = (ImageButton) rootView.findViewById(R.id.ibClear);
        ibChangeDate = (ImageButton) rootView.findViewById(R.id.ibChangeDate);
        txtParticularCustomerGasSize = (EditText) rootView.findViewById(R.id.txtParticularCustomerGasSize);
        txtParticularCustomerDeliveryCharge = (EditText) rootView.findViewById(R.id.txtParticularCustomerDeliveryCharge);
        txtParticularCustomerDOB = (EditText) rootView.findViewById(R.id.txtParticularCustomerDOB);
        txtParticularCustomerEmailId = (EditText) rootView.findViewById(R.id.txtParticularCustomerEmailId);
        txtParticularCustomerSecondaryPhoneNumber = (EditText) rootView.findViewById(R.id.txtParticularCustomerSecondaryPhoneNumber);
        txtParticularCustomerId = (EditText) rootView.findViewById(R.id.txtParticularCustomerId);
        txtParticularCustomerName = (EditText) rootView.findViewById(R.id.txtParticularCustomerName);
        txtParticularCustomerAddress = (EditText) rootView.findViewById(R.id.txtParticularCustomerAddress);
        txtParticularCustomerGasType = (EditText) rootView.findViewById(R.id.txtParticularCustomerGasType);
        txtParticularCustomerPhoneNumber = (EditText) rootView.findViewById(R.id.txtParticularCustomerPhoneNumber);
        txtParticularCustomerFirstName = (EditText) rootView.findViewById(R.id.txtParticularCustomerFirstName);
        txtParticularCustomerMiddleName = (EditText) rootView.findViewById(R.id.txtParticularCustomerMiddleName);
        txtParticularCustomerLastName = (EditText) rootView.findViewById(R.id.txtParticularCustomerLastName);
        txtParticularCustomerLatLng = (EditText) rootView.findViewById(R.id.txtParticularCustomerLatLng);
        txtParticularCustomerLandmark = (EditText) rootView.findViewById(R.id.txtParticularCustomerLandmark);
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(txtParticularCustomerId.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        spnParticularCustomerGasType = (Spinner) rootView.findViewById(R.id.spnParticularCustomerGasType);
        spnParticularCustomerGasSize = (Spinner) rootView.findViewById(R.id.spnParticularCustomerGasSize);

        lblCName = (TextView) rootView.findViewById(R.id.lblCName);

        llParticularFirstName = (LinearLayout) rootView.findViewById(R.id.llParticularFirstName);
        llParticularMiddleName = (LinearLayout) rootView.findViewById(R.id.llParticularMiddleName);
        llParticularLastName = (LinearLayout) rootView.findViewById(R.id.llParticularLastName);
        svParticularCustomerDetail = (ScrollView) rootView.findViewById(R.id.svParticularCustomerDetail);

        txtParticularCustomerId.setEnabled(false);
        txtParticularCustomerLandmark.setEnabled(false);
        txtParticularCustomerName.setEnabled(false);
        txtParticularCustomerDOB.setEnabled(false);
        txtParticularCustomerSecondaryPhoneNumber.setEnabled(false);
        txtParticularCustomerEmailId.setEnabled(false);
        txtParticularCustomerAddress.setEnabled(false);
        txtParticularCustomerGasType.setEnabled(false);
        txtParticularCustomerPhoneNumber.setEnabled(false);
        txtParticularCustomerLatLng.setEnabled(false);
        txtParticularCustomerGasSize.setEnabled(false);
        txtParticularCustomerDeliveryCharge.setEnabled(false);
        btnParticularSave.setOnClickListener(this);
        ibClear.setOnClickListener(this);
        ibChangeDate.setOnClickListener(this);
        btnDirection.setOnClickListener(this);
        txtParticularCustomerSecondaryPhoneNumber.setOnClickListener(this);
        txtParticularCustomerPhoneNumber.setOnClickListener(this);
        SettingGasSize(ParticularCustomerDetailInfo.customerDetail.getGasTypeId());
        spnParticularCustomerGasType.setSelection(Integer.parseInt(ParticularCustomerDetailInfo.customerDetail.getGasTypeId())-1);
        txtParticularCustomerPhoneNumber.setPaintFlags(txtParticularCustomerSecondaryPhoneNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        spnParticularCustomerGasType.setOnItemSelectedListener(this);
        spnParticularCustomerGasSize.setOnItemSelectedListener(this);
        txtParticularCustomerId.setText(ParticularCustomerDetailInfo.customerDetail.getCustomerId());
        txtParticularCustomerLandmark.setText(ParticularCustomerDetailInfo.customerDetail.getLandmark());
        txtParticularCustomerName.setText(ParticularCustomerDetailInfo.customerDetail.getFullName());
        String[] name = ParticularCustomerDetailInfo.customerDetail.getFullName().split(" ");
        txtParticularCustomerFirstName.setText(ParticularCustomerDetailInfo.customerDetail.getFirstName());
        txtParticularCustomerMiddleName.setText(ParticularCustomerDetailInfo.customerDetail.getMiddleName());
        txtParticularCustomerLastName.setText(ParticularCustomerDetailInfo.customerDetail.getLastName());
        txtParticularCustomerAddress.setText(ParticularCustomerDetailInfo.customerDetail.getAddress());
        txtParticularCustomerPhoneNumber.setText(ParticularCustomerDetailInfo.customerDetail.getPhoneNumber());
        if(ParticularCustomerDetailInfo.customerDetail.getGasTypeId().equals("1")){
            txtParticularCustomerGasType.setText("Domestic");
        }else{
            txtParticularCustomerGasType.setText("Commercial");
        }
        txtParticularCustomerGasSize.setText(ParticularCustomerDetailInfo.customerDetail.getGasSize().getGasSize());
        if (ParticularCustomerDetailInfo.customerDetail.getEmail()!=null) {
            txtParticularCustomerEmailId.setText(ParticularCustomerDetailInfo.customerDetail.getEmail());
        } else {
            txtParticularCustomerEmailId.setText("--");
        }
        if (ParticularCustomerDetailInfo.customerDetail.getSecondMobileNumber()!=null) {
            txtParticularCustomerSecondaryPhoneNumber.setPaintFlags(txtParticularCustomerSecondaryPhoneNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtParticularCustomerSecondaryPhoneNumber.setTextColor(Color.BLUE);
            txtParticularCustomerSecondaryPhoneNumber.setText(ParticularCustomerDetailInfo.customerDetail.getSecondMobileNumber());
        } else {
            txtParticularCustomerSecondaryPhoneNumber.setText("--");
        }
        if (!ParticularCustomerDetailInfo.customerDetail.getDob().equals("0000-00-00")) {
            txtParticularCustomerDOB.setText(ParticularCustomerDetailInfo.customerDetail.getDob());
        } else {
            txtParticularCustomerDOB.setText("--");
        }
        if(!(ParticularCustomerDetailInfo.customerDetail.getDeliveryCharge().equals("0"))){
            txtParticularCustomerDeliveryCharge.setText(ParticularCustomerDetailInfo.customerDetail.getDeliveryCharge()+"");
        }else{
            txtParticularCustomerDeliveryCharge.setText("0");
        }
        if (!(ParticularCustomerDetailInfo.customerDetail.getLatLng().equals("0"))) {
            if (ParticularCustomerDetailInfo.customerDetail.getLatLng().equals("")) {
                txtParticularCustomerLatLng.setText("No LatLng Available");
                btnDirection.setVisibility(View.GONE);
                ibClear.setVisibility(View.GONE);
            } else {
                txtParticularCustomerLatLng.setText(ParticularCustomerDetailInfo.customerDetail.getLatLng());
                btnDirection.setVisibility(View.VISIBLE);
                ibClear.setVisibility(View.VISIBLE);
            }
        } else {
            txtParticularCustomerLatLng.setText("No LatLng Available");
            btnDirection.setVisibility(View.GONE);
            ibClear.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtParticularCustomerPhoneNumber:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + txtParticularCustomerPhoneNumber.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(callIntent);
                break;
            case R.id.txtParticularCustomerSecondaryPhoneNumber:
                if (txtParticularCustomerSecondaryPhoneNumber.getText().equals("--")) {

                } else {
                    Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                    callIntent1.setData(Uri.parse("tel:" + txtParticularCustomerSecondaryPhoneNumber.getText().toString()));
                    callIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    getActivity().startActivity(callIntent1);
                }
                break;
            case R.id.btnParticularSave:
                final ProgressDialog pDialog = new ProgressDialog(getActivity());
                pDialog.setTitle("Saving Information");
                pDialog.setMessage("Please Wait a Minute");
                pDialog.setCanceledOnTouchOutside(false);
                lblCName.setVisibility(View.VISIBLE);
                txtParticularCustomerName.setVisibility(View.VISIBLE);
                ibChangeDate.setVisibility(View.GONE);
                llParticularFirstName.setVisibility(View.GONE);
                llParticularLastName.setVisibility(View.GONE);
                llParticularMiddleName.setVisibility(View.GONE);
                btnParticularSave.setVisibility(View.GONE);
                ParticularCustomerDetailInfo.btnEditCustomerDetail.setVisibility(View.VISIBLE);
                txtParticularCustomerId.setEnabled(false);
                txtParticularCustomerLandmark.setEnabled(false);
                txtParticularCustomerDeliveryCharge.setEnabled(false);
                txtParticularCustomerName.setEnabled(false);
                txtParticularCustomerDOB.setEnabled(false);
                txtParticularCustomerEmailId.setEnabled(false);
                txtParticularCustomerSecondaryPhoneNumber.setEnabled(false);
                txtParticularCustomerAddress.setEnabled(false);
                txtParticularCustomerGasType.setEnabled(false);
                txtParticularCustomerPhoneNumber.setEnabled(false);
                txtParticularCustomerGasType.setVisibility(View.VISIBLE);
                spnParticularCustomerGasType.setVisibility(View.GONE);
                txtParticularCustomerGasSize.setVisibility(View.VISIBLE);
                spnParticularCustomerGasSize.setVisibility(View.GONE);
                if(Utils.isInternetAvailable(getActivity())) {
                    pDialog.show();
                    queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                    String url = AppController.mainUrl+AppController.API_MODIFY_CUSTOMER+ParticularCustomerDetailInfo.customerDetail.getId();
                    Type type = null;
                    baseModel<String> bm = new baseModel(Request.Method.POST,url, null, type ,new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            Log.e("response", response.toString());
                            if(response.toString().equals("200"))
                                Snackbar.make(svParticularCustomerDetail, "Data Successfully Saved", Snackbar.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("response error",error.toString());
                            pDialog.dismiss();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put(AppController.FIRSTNAME, txtParticularCustomerFirstName.getText().toString());
                            param.put(AppController.MIDDLENAME,txtParticularCustomerMiddleName.getText().toString());
                            param.put(AppController.LASTNAME,txtParticularCustomerLastName.getText().toString());
                            param.put(AppController.ADDRESS,txtParticularCustomerAddress.getText().toString());
                            param.put(AppController.LANDMARK,txtParticularCustomerLandmark.getText().toString());
                            param.put(AppController.PHONE_NUMBER,txtParticularCustomerPhoneNumber.getText().toString());
//                            param.put(AppController.CUSTOMERID, ParticularCustomerDetailInfo.customerDetail.getCustomerId());
//                            param.put(AppController.GB_USER_ID, ParticularCustomerDetailInfo.customerDetail.getUserId());
                            param.put(AppController.GAS_TYPE_ID,"1");
                            param.put(AppController.GAS_SIZE_ID,"1");
                            if(txtParticularCustomerDeliveryCharge.getText()==null || txtParticularCustomerDeliveryCharge.getText().toString().length()<=0)
                                param.put(AppController.DELIVERY_CHARGE,"0");
                            else
                                param.put(AppController.DELIVERY_CHARGE,txtParticularCustomerDeliveryCharge.getText().toString());
                            if (txtParticularCustomerEmailId.getText() != null && txtParticularCustomerEmailId.getText().toString().length() > 0)
                                param.put(AppController.EMAIL,txtParticularCustomerEmailId.getText().toString());
                            if (txtParticularCustomerSecondaryPhoneNumber.getText() != null && txtParticularCustomerSecondaryPhoneNumber.getText().toString().length() > 0)
                                param.put(AppController.SECOND_MOBILE_NUMBER,txtParticularCustomerSecondaryPhoneNumber.getText().toString());
                            if (txtParticularCustomerDOB.getText() != null && txtParticularCustomerDOB.getText().toString().length() > 0) {
                                String[] date = txtParticularCustomerDOB.getText().toString().split("-");
                                if(date[0].length()<=1)
                                    date[0] = "0"+date[0];
                                if(date[1].length()<=1)
                                    date[1] = "0"+date[1];
                                param.put(AppController.DOB, date[2]+"-"+date[1]+"-"+date[0]);
                            }

                            return param;
                        }
                    };

                    queue.add(bm);
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.e("response ", response);
//                            if(response.toString().equals("200"))
//                                Snackbar.make(svParticularCustomerDetail, "Data Successfully Saved", Snackbar.LENGTH_LONG).show();
//                            pDialog.dismiss();
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.e("response error",error.toString());
//                            pDialog.dismiss();
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> param = new HashMap<>();
//                            param.put(AppController.FIRSTNAME, txtParticularCustomerFirstName.getText().toString());
//                            param.put(AppController.MIDDLENAME,txtParticularCustomerMiddleName.getText().toString());
//                            param.put(AppController.LASTNAME,txtParticularCustomerLastName.getText().toString());
//                            param.put(AppController.ADDRESS,txtParticularCustomerAddress.getText().toString());
//                            param.put(AppController.LANDMARK,txtParticularCustomerLandmark.getText().toString());
//                            param.put(AppController.PHONE_NUMBER,txtParticularCustomerPhoneNumber.getText().toString());
////                            param.put(AppController.CUSTOMERID, ParticularCustomerDetailInfo.customerDetail.getCustomerId());
////                            param.put(AppController.GB_USER_ID, ParticularCustomerDetailInfo.customerDetail.getUserId());
//                            param.put(AppController.GAS_TYPE_ID,"1");
//                            param.put(AppController.GAS_SIZE_ID,"1");
//                            if(txtParticularCustomerDeliveryCharge.getText()==null || txtParticularCustomerDeliveryCharge.getText().toString().length()<=0)
//                                param.put(AppController.DELIVERY_CHARGE,"0");
//                            else
//                                param.put(AppController.DELIVERY_CHARGE,txtParticularCustomerDeliveryCharge.getText().toString());
//                            if (txtParticularCustomerEmailId.getText() != null && txtParticularCustomerEmailId.getText().toString().length() > 0)
//                                param.put(AppController.EMAIL,txtParticularCustomerEmailId.getText().toString());
//                            if (txtParticularCustomerSecondaryPhoneNumber.getText() != null && txtParticularCustomerSecondaryPhoneNumber.getText().toString().length() > 0)
//                                param.put(AppController.SECOND_MOBILE_NUMBER,txtParticularCustomerSecondaryPhoneNumber.getText().toString());
//                            if (txtParticularCustomerDOB.getText() != null && txtParticularCustomerDOB.getText().toString().length() > 0) {
//                                String[] date = txtParticularCustomerDOB.getText().toString().split("/");
//                                if(date[0].length()<=1)
//                                    date[0] = "0"+date[0];
//                                if(date[1].length()<=1)
//                                    date[1] = "0"+date[1];
//                                param.put(AppController.DOB, date[2]+"-"+date[1]+"-"+date[0]);
//                            }
//                            return param;
//                        }
//                    };
//                    queue.add(stringRequest);
                }else {
                    Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnDirection:
                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {
                    currentLatitude = gps.getLatitude();
                    currentLongitude = gps.getLongitude();
                    Intent next = new Intent(getActivity(),MapActivity.class);
                    next.putExtra("cLat",currentLatitude);
                    next.putExtra("cLon", currentLongitude);
                    next.putExtra("userLatLng", txtParticularCustomerLatLng.getText().toString());
                    next.putExtra("ObjectId",ParticularCustomerDetailInfo.CustomerId);
                    startActivity(next);
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+currentLatitude+","+currentLongitude+"&daddr="+txtParticularCustomerLatLng.getText().toString()));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                    startActivity(intent);
                }else {
                    gps.showSettingsAlert();
                }
                break;
            case R.id.ibClear:
                AlertDialog.Builder aDialog = new AlertDialog.Builder(getActivity());
                aDialog.setTitle("Alert");
                aDialog.setMessage("Are you sure want to delete LatLng?");
                aDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("Removing LatLng");
                        progressDialog.setMessage("Please wait");
                        progressDialog.setCanceledOnTouchOutside(false);
                        if(Utils.isInternetAvailable(getActivity())) {
                            progressDialog.show();
                            queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                            String url = AppController.mainUrl+AppController.API_MODIFY_CUSTOMER+Integer.parseInt(txtParticularCustomerId.getText().toString());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response ", response);
                                    if(response.toString().equals("200")) {
                                        Snackbar.make(svParticularCustomerDetail, "Data Successfully Saved", Snackbar.LENGTH_LONG).show();
                                        txtParticularCustomerLatLng.setText("No LatLng Available");
                                        btnDirection.setVisibility(View.GONE);
                                        ibClear.setVisibility(View.GONE);
                                    }
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("response error",error.toString());
                                    progressDialog.dismiss();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();
                                    param.put(AppController.FIRSTNAME, txtParticularCustomerFirstName.getText().toString());
                                    param.put(AppController.MIDDLENAME,txtParticularCustomerMiddleName.getText().toString());
                                    param.put(AppController.LASTNAME,txtParticularCustomerLastName.getText().toString());
                                    param.put(AppController.ADDRESS,txtParticularCustomerAddress.getText().toString());
                                    param.put(AppController.LANDMARK,txtParticularCustomerLandmark.getText().toString());
                                    param.put(AppController.PHONE_NUMBER,txtParticularCustomerPhoneNumber.getText().toString());
//                            param.put(AppController.CUSTOMERID, txtParticularCustomerCustomerId.getText().toString());
                                    param.put(AppController.GAS_TYPE_ID,"1");
                                    param.put(AppController.PHONE_NUMBER,txtParticularCustomerPhoneNumber.getText().toString());
                                    param.put(AppController.GAS_SIZE_ID,"1");
                                    param.put(AppController.LATLNG,"");
                                    if(txtParticularCustomerDeliveryCharge.getText()==null || txtParticularCustomerDeliveryCharge.getText().toString().length()<=0)
                                        param.put(AppController.DELIVERY_CHARGE,"0");
                                    else
                                        param.put(AppController.DELIVERY_CHARGE,txtParticularCustomerDeliveryCharge.getText().toString());
                                    if (txtParticularCustomerEmailId.getText() != null && txtParticularCustomerEmailId.getText().toString().length() > 0)
                                        param.put(AppController.EMAIL,txtParticularCustomerEmailId.getText().toString());
                                    if (txtParticularCustomerSecondaryPhoneNumber.getText() != null && txtParticularCustomerSecondaryPhoneNumber.getText().toString().length() > 0)
                                        param.put(AppController.SECOND_MOBILE_NUMBER,txtParticularCustomerSecondaryPhoneNumber.getText().toString());
                                    if (txtParticularCustomerDOB.getText() != null && txtParticularCustomerDOB.getText().toString().length() > 0)
                                        param.put(AppController.DOB,txtParticularCustomerDOB.getText().toString());
                                    return param;
                                }
                            };
                            queue.add(stringRequest);
                        }else {
                            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                aDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aDialog.show();
                break;
            case R.id.ibChangeDate:
                DatePickerDialog dpDialog = new DatePickerDialog(getActivity(),this,year,month,day);
                dpDialog.getDatePicker().setCalendarViewShown(false);
                dpDialog.getDatePicker().setSpinnersShown(true);
                dpDialog.show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnParticularCustomerGasSize:
                break;
            case R.id.spnParticularCustomerGasType:
                pos = position;
                SettingGasSize((pos+1)+"");
                break;
        }
//        pos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void SettingGasSize(final String GasTypeId) {
        if(Utils.isInternetAvailable(getActivity())) {
            queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
            String url = AppController.mainUrl+AppController.API_GET_GAS_SIZE;
            Type type =  new TypeToken<List<GasSizeDetailModel>>(){}.getType();
            baseModel bm = new baseModel(Request.Method.POST,url, null, type ,new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Log.e("response", response.toString());
                    if(gasSizeDetailModel.size()>0)
                        gasSizeDetailModel.clear();
                    gasSizeDetailModel.addAll((ArrayList<GasSizeDetailModel>) response);
                    Log.e("response ", "");
//                    swiperefreshAdmin.setRefreshing(false);
                    String[] Gas = new String[gasSizeDetailModel.size()];
                    for (int i = 0; i < gasSizeDetailModel.size(); i++) {
                        Gas[i] = String.valueOf(gasSizeDetailModel.get(i).getGasSize());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Gas);
                    int pos = spnParticularCustomerGasSize.getSelectedItemPosition();
                    spnParticularCustomerGasSize.setAdapter(adapter);
                    if(GasTypeId.equals("1"))
                        spnParticularCustomerGasSize.setSelection(pos);
                    else {
                        if(pos==3)
                            spnParticularCustomerGasSize.setSelection((pos-1));
                        else
                            spnParticularCustomerGasSize.setSelection(pos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response error",error.toString());
//                    swiperefreshAdmin.setRefreshing(false);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put(AppController.GAS_TYPE_ID, GasTypeId);

                    return param;
                }
            };

            queue.add(bm);
            /*ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
            gasSizeQuery.whereEqualTo(AppConstant.USER_GAS_TYPE_ID, GasTypeId);
            gasSizeQuery.findInBackground(new FindCallback<GasSize>() {
                @Override
                public void done(List<GasSize> gasSize, ParseException e) {
                    if (e == null) {
                        if (gasSize.size() > 0) {
                            if (gasSizeList.size() > 0) {
                                gasSizeList.clear();
                            }
                            gasSizeList.addAll(gasSize);
                            String[] Gas = new String[gasSize.size()];
                            for (int i = 0; i < gasSize.size(); i++) {
                                Gas[i] = String.valueOf(gasSize.get(i).getGasSize());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Gas);
                            int pos = spnParticularCustomerGasSize.getSelectedItemPosition();
                            spnParticularCustomerGasSize.setAdapter(adapter);
                            if(GasTypeId==1)
                                spnParticularCustomerGasSize.setSelection(pos);
                            else {
                                if(pos==3)
                                    spnParticularCustomerGasSize.setSelection((pos-1));
                                else
                                    spnParticularCustomerGasSize.setSelection(pos);
                            }
                        }
                    }
                }
            });*/
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        txtParticularCustomerDOB.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        this.day = dayOfMonth;
        this.month = monthOfYear;
        this.year = year;
    }
    public static void Edit(){
                txtParticularCustomerId.setEnabled(true);
                txtParticularCustomerLandmark.setEnabled(true);
                txtParticularCustomerName.setEnabled(true);
                txtParticularCustomerDOB.setEnabled(true);
                txtParticularCustomerDeliveryCharge.setEnabled(true);
                txtParticularCustomerEmailId.setEnabled(true);
                txtParticularCustomerSecondaryPhoneNumber.setEnabled(true);
                txtParticularCustomerAddress.setEnabled(true);
                txtParticularCustomerGasType.setEnabled(true);
                txtParticularCustomerPhoneNumber.setEnabled(true);
                btnParticularSave.setVisibility(View.VISIBLE);
//                fabEditCustomerDetail.setVisibility(View.GONE);
                lblCName.setVisibility(View.GONE);
                txtParticularCustomerName.setVisibility(View.GONE);
                ibChangeDate.setVisibility(View.VISIBLE);
                llParticularFirstName.setVisibility(View.VISIBLE);
                llParticularLastName.setVisibility(View.VISIBLE);
                llParticularMiddleName.setVisibility(View.VISIBLE);
                txtParticularCustomerGasType.setVisibility(View.GONE);
                spnParticularCustomerGasType.setVisibility(View.VISIBLE);
                txtParticularCustomerGasSize.setVisibility(View.GONE);
                spnParticularCustomerGasSize.setVisibility(View.VISIBLE);

    }
}
