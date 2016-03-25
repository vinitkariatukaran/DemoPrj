package com.demoprj;

import android.*;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demoprj.BaseRequest.RestModel.TransactionDetail;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.GsonModel.PACK.GasSizeDetailModel;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mounil on 07/01/2016.
 */
public class ParticularDeliveryDetail extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView lblParticularDeliveryCustomerId;
    TextView lblParticularDeliveryCustomerName;
    TextView lblParticularDeliveryCustomerLandmark;
    TextView lblParticularDeliveryGasType;
    TextView lblParticularDeliveryGasPrice;
    TextView lblParticularDeliveryGasSize;
    TextView lblParticularDeliveryGasQuantity;
    TextView lblParticularDeliveryServiceNote;
    TextView lblParticularDeliveryServiceCharge;
    TextView lblParticularDeliveryCustomerBDate;
    TextView lblParticularDeliveryCustomerDDate;
    TextView lblParticularDeliveryBoy;
    TextView lblParticularDeliveryCharge;
    TextView lblParticularDeliveryTotal;
    TextView lblParticularDeliveryCustomePhNo;

    LinearLayout llParticularDeliveryDate;
    LinearLayout llParticularDeliveryTypeQuantity;
    LinearLayout llParticularDeliverySizePrice;
    LinearLayout llParticularDeliveryChargeTotal;
    LinearLayout llParticularDeliveryServiceNote;
    LinearLayout llParticularDeliveryServiceCharge;
    LinearLayout llParticularDeliveryLayout;

    Spinner spnParticularDeliveryBoy;
    String ObjectId;
    String GasObjectId;
    Button btnEditDeliveryBoy;
    Button btnParticularDeliverySave;
    Button btnDeleteDeliveryDetail;
    ArrayList<ParseUser> DeliveryBoyList = new ArrayList<ParseUser>();
    String DeliveryBoy;
    NestedScrollView sv;
    TransactionDetail details = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.particular_delivery_detail);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            GasObjectId = b.getString("ObjectId");
            details = (TransactionDetail) b.getSerializable("details");
        }
        llParticularDeliveryDate = (LinearLayout) findViewById(R.id.llParticularDeliveryDate);
        llParticularDeliveryChargeTotal = (LinearLayout) findViewById(R.id.llParticularDeliveryChargeTotal);
        llParticularDeliveryTypeQuantity = (LinearLayout) findViewById(R.id.llParticularDeliveryTypeQuantity);
        llParticularDeliverySizePrice = (LinearLayout) findViewById(R.id.llParticularDeliverySizePrice);
        llParticularDeliveryServiceNote = (LinearLayout) findViewById(R.id.llParticularDeliveryServiceNote);
        llParticularDeliveryServiceCharge = (LinearLayout) findViewById(R.id.llParticularDeliveryServiceCharge);
        llParticularDeliveryLayout = (LinearLayout) findViewById(R.id.llParticularDeliveryLayout);
        sv = (NestedScrollView) findViewById(R.id.sv);
        sv.setBackgroundColor(Color.WHITE);
        btnParticularDeliverySave = (Button) findViewById(R.id.btnParticularDeliverySave);
        btnEditDeliveryBoy = (Button) findViewById(R.id.btnEditDeliveryBoy);
        btnDeleteDeliveryDetail = (Button) findViewById(R.id.btnDeleteDeliveryDetail);

        lblParticularDeliveryCustomePhNo = (TextView) findViewById(R.id.lblParticularDeliveryCustomePhNo);
        lblParticularDeliveryTotal = (TextView) findViewById(R.id.lblParticularDeliveryTotal);
        lblParticularDeliveryCharge = (TextView) findViewById(R.id.lblParticularDeliveryCharge);
        lblParticularDeliveryCustomerBDate = (TextView) findViewById(R.id.lblParticularDeliveryCustomerBDate);
        lblParticularDeliveryCustomerDDate = (TextView) findViewById(R.id.lblParticularDeliveryCustomerDDate);
        lblParticularDeliveryServiceCharge = (TextView) findViewById(R.id.lblParticularDeliveryServiceCharge);
        lblParticularDeliveryServiceNote = (TextView) findViewById(R.id.lblParticularDeliveryServiceNote);
        lblParticularDeliveryCustomerId = (TextView) findViewById(R.id.lblParticularDeliveryCustomerId);
        lblParticularDeliveryCustomerName = (TextView) findViewById(R.id.lblParticularDeliveryCustomerName);
        lblParticularDeliveryCustomerLandmark = (TextView) findViewById(R.id.lblParticularDeliveryCustomerLandmark);
        lblParticularDeliveryGasType = (TextView) findViewById(R.id.lblParticularDeliveryGasType);
        lblParticularDeliveryGasPrice = (TextView) findViewById(R.id.lblParticularDeliveryGasPrice);
        lblParticularDeliveryGasSize = (TextView) findViewById(R.id.lblParticularDeliveryGasSize);
        lblParticularDeliveryGasQuantity = (TextView) findViewById(R.id.lblParticularDeliveryGasQuantity);
        lblParticularDeliveryBoy = (TextView) findViewById(R.id.lblParticularDeliveryBoy);

        spnParticularDeliveryBoy = (Spinner) findViewById(R.id.spnParticularDeliveryBoy);

        btnEditDeliveryBoy.setOnClickListener(this);
        btnParticularDeliverySave.setOnClickListener(this);
        btnDeleteDeliveryDetail.setOnClickListener(this);
        lblParticularDeliveryCustomePhNo.setOnClickListener(this);

        spnParticularDeliveryBoy.setOnItemSelectedListener(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Detail");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        if (Utils.isInternetAvailable(this)) {
//            progressDialog.show();
//            ParseQuery<GasBooking> GasQuery = ParseQuery.getQuery(GasBooking.class);
//            GasQuery.whereEqualTo(AppConstant.OBJECT_ID,GasObjectId);
//            GasQuery.findInBackground(new FindCallback<GasBooking>() {
//                @Override
//                public void done(List<GasBooking> objects, ParseException e) {
//                    if(e==null){
//                        if(objects.size()>0){
//                            GasBooking gasBooking = objects.get(0);
//                            User u = (User) gasBooking.get(AppConstant.GASBOOKING_USER_ID);
        lblParticularDeliveryCustomerId.setText(details.getUserInfoId() + "");
        lblParticularDeliveryCustomerName.setText(details.getFullName());
        lblParticularDeliveryCustomePhNo.setText(details.getPhoneNumber() + "");
        lblParticularDeliveryCustomePhNo.setTextColor(Color.BLUE);
        lblParticularDeliveryCustomePhNo.setPaintFlags(lblParticularDeliveryCustomePhNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lblParticularDeliveryCustomerLandmark.setText(details.getLandmark());
        if (!details.getIsService().equals(1)) {
//                                final GasType gasType = (GasType) u.get(AppConstant.USER_GAS_TYPE_ID);
//                                ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
//                                gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasType.getObjectId());
//                                gasTypeQuery.findInBackground(new FindCallback<GasType>() {
//                                    @Override
//                                    public void done(List<GasType> objects, ParseException e) {
//                                        if (e == null) {
//                                            if (objects.size() > 0) {
            lblParticularDeliveryGasType.setText(details.getGasType());
//                                            }
//                                        } else {
//
//                                        }
//                                    }
//                                });
        }
        boolean deleiveryStatus = details.getDateOfDelivery().equals("0000-00-00 00:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm aa");
        if (!deleiveryStatus) {
            Date DeliveryDate = getDate(details.getDateOfDelivery());
            Date BookingDate = getDate(details.getDateOfBooking());
            Log.e("Booking & deliverDate", BookingDate + "    :::::    " + DeliveryDate);
            llParticularDeliveryDate.setVisibility(View.VISIBLE);
            lblParticularDeliveryCustomerBDate.setText(sdf.format(BookingDate));
            lblParticularDeliveryCustomerDDate.setText(sdf.format(DeliveryDate));
        } else {
            Date BookingDate1 = getDate(details.getDateOfBooking());
            Log.e("Booking ", BookingDate1 + "");
            lblParticularDeliveryCustomerBDate.setText(sdf.format(BookingDate1));
            llParticularDeliveryDate.setVisibility(View.GONE);
        }

        if (details.getIsService().equals(1)) {
            llParticularDeliveryServiceCharge.setVisibility(View.VISIBLE);
            llParticularDeliveryServiceNote.setVisibility(View.VISIBLE);
            llParticularDeliverySizePrice.setVisibility(View.GONE);
            llParticularDeliveryChargeTotal.setVisibility(View.GONE);
            llParticularDeliveryTypeQuantity.setVisibility(View.GONE);
            lblParticularDeliveryServiceCharge.setText(details.getServiceCharge());
            lblParticularDeliveryServiceNote.setText((String) details.getServiceNote());
        } else {
            llParticularDeliveryServiceCharge.setVisibility(View.GONE);
            llParticularDeliveryServiceNote.setVisibility(View.GONE);
            llParticularDeliverySizePrice.setVisibility(View.VISIBLE);
            llParticularDeliveryChargeTotal.setVisibility(View.VISIBLE);
            llParticularDeliveryTypeQuantity.setVisibility(View.VISIBLE);
            double total = Double.parseDouble(details.getTotal());
            if (!details.getDeliveryCharge().equals("")) {
                double charge = (double) Integer.parseInt(details.getDeliveryCharge());
                lblParticularDeliveryGasPrice.setText((total - charge) + "");
                lblParticularDeliveryCharge.setText(charge + "");
                lblParticularDeliveryTotal.setText(total + "");
            } else {
                lblParticularDeliveryGasPrice.setText(total + "");
                lblParticularDeliveryCharge.setText("0");
                lblParticularDeliveryTotal.setText(total + "");
            }

//                GasSize gasSize = (GasSize) gasBooking.get(AppConstant.GASBOOKING_GAS_SIZE_ID);
//                if (gasSize != null) {
            lblParticularDeliveryGasSize.setText(details.getGasSize() + "");
//                }
            lblParticularDeliveryGasQuantity.setText(details.getQuantity() + "");
        }
//            ParseUser db = (ParseUser) gasBooking.get(AppConstant.GASBOOKING_DELIVERYBOY_ID);
//            DeliveryBoy = db.getUsername();
        lblParticularDeliveryBoy.setText("DEl boy 2");
//            for (int i = 0; i < DeliveryBoyList.size(); i++) {
//                if (DeliveryBoyList.get(i).getObjectId().equals(db.getObjectId())) {
//                    spnParticularDeliveryBoy.setSelection(i);
//                    break;
//                }
//            }

//            Delevery boy list pending VINIT K
//        } else {
//
//        }
//                }
//            });
//        }else{
//
//        }

//        Pending Vinit K
//        if (Utils.isInternetAvailable(this)) {
//            ParseQuery<ParseUser> deliveryBotQuery = ParseQuery.getQuery(ParseUser.class);
//            deliveryBotQuery.whereEqualTo(AppConstant.IS_ADMIN, false);
//            deliveryBotQuery.findInBackground(new FindCallback<ParseUser>() {
//                @Override
//                public void done(List<ParseUser> objects, ParseException e) {
//                    if (e == null) {
//                        if (objects.size() > 0) {
//                            DeliveryBoyList.addAll(objects);
//                            ArrayList<String> DeliveryBoy = new ArrayList<String>();
//                            for (int i = 0; i < objects.size(); i++) {
//                                DeliveryBoy.add(objects.get(i).getUsername());
//                            }
//                            ArrayAdapter adapter = new ArrayAdapter(ParticularDeliveryDetail.this, android.R.layout.simple_dropdown_item_1line, DeliveryBoy);
//                            spnParticularDeliveryBoy.setAdapter(adapter);
//                        }
//                    } else {
//
//                    }
//                    progressDialog.dismiss();
//                }
//            });
//        } else {
//            Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
//        }
    }

    private Date getDate(String dateOfDelivery) {
//        0000-00-00 00:00:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(dateOfDelivery);
            System.out.println(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblParticularDeliveryCustomePhNo:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + lblParticularDeliveryCustomePhNo.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
                break;
            case R.id.btnParticularDeliverySave:
                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setTitle("Saving Information");
                pDialog.setMessage("Please Wait");
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);
                pDialog.show();

                RequestQueue queue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                String url = AppController.mainUrl + AppController.API_CHANGE_DELIVERYBOY;
                Type type = null;
                baseModel<String> bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.e("response", response.toString());
                        Snackbar.make(llParticularDeliveryLayout, "Data Successfully Save", Snackbar.LENGTH_LONG).show();
                        lblParticularDeliveryBoy.setText("changed");
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response error", error.toString());
                        pDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put(AppController.GAS_BOOKING_ID, details.getGasBookingId());
                        param.put(AppController.BOY_ID, details.getUserId());
                        return param;
                    }
                };
                queue.add(bm);

//                ParseQuery<GasBooking> gasQuery = ParseQuery.getQuery(GasBooking.class);
//                gasQuery.whereEqualTo(AppConstant.OBJECT_ID, GasObjectId);
//                gasQuery.findInBackground(new FindCallback<GasBooking>() {
//                    @Override
//                    public void done(List<GasBooking> objects, ParseException e) {
//                        if (e == null) {
//                            if (objects.size() > 0) {
//                                objects.get(0).setDeliveryBoyId(ObjectId);
//                                objects.get(0).saveInBackground(new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException e) {
//                                        if (e == null) {
//                                            Snackbar.make(llParticularDeliveryLayout, "Data Successfully Save", Snackbar.LENGTH_LONG).show();
//                                            lblParticularDeliveryBoy.setText(DeliveryBoy);
//                                        } else {
//
//                                        }
//                                        pDialog.dismiss();
//                                    }
//                                });
//                            }
//                        } else {
//
//                        }
//                    }
//                });
                btnEditDeliveryBoy.setVisibility(View.VISIBLE);
                lblParticularDeliveryBoy.setVisibility(View.VISIBLE);
                btnParticularDeliverySave.setVisibility(View.GONE);
                spnParticularDeliveryBoy.setVisibility(View.GONE);
                break;
            case R.id.btnEditDeliveryBoy:
                btnEditDeliveryBoy.setVisibility(View.GONE);
                lblParticularDeliveryBoy.setVisibility(View.GONE);
                btnParticularDeliverySave.setVisibility(View.VISIBLE);
                spnParticularDeliveryBoy.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDeleteDeliveryDetail:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setCancelable(false);
                ad.setTitle("Alert");
                ad.setMessage("Are you sure want to delete this Delivery");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(ParticularDeliveryDetail.this);
                        progressDialog.setTitle("Deleting Record");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        if (Utils.isInternetAvailable(ParticularDeliveryDetail.this)) {
//                            progressDialog.show();
//                            ParseQuery<GasBooking> gasBookingDeleteQuery = ParseQuery.getQuery(GasBooking.class);
//                            gasBookingDeleteQuery.whereEqualTo(AppConstant.OBJECT_ID, GasObjectId);
//                            gasBookingDeleteQuery.findInBackground(new FindCallback<GasBooking>() {
//                                @Override
//                                public void done(List<GasBooking> objects, ParseException e) {
//                                    if (e == null) {
//                                        if (objects.size() > 0) {
//                                            objects.get(0).deleteInBackground(new DeleteCallback() {
//                                                @Override
//                                                public void done(ParseException e) {
//                                                    if (e == null) {
//                                                        Toast.makeText(ParticularDeliveryDetail.this, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
//                                                        finish();
//                                                    } else {
//
//                                                    }
//                                                    progressDialog.dismiss();
//                                                }
//                                            });
//                                        }
//                                    } else {
//                                        progressDialog.dismiss();
//                                    }
//                                }
//                            });
                            RequestQueue queue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                            String url = AppController.mainUrl + AppController.API_GAS_BOOKING_DELETE;
                            Type type = null;
                            baseModel<String> bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    Log.e("response", response.toString());
                                    Snackbar.make(llParticularDeliveryLayout, "Data Successfully Save", Snackbar.LENGTH_LONG).show();
                                    lblParticularDeliveryBoy.setText("changed");
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("response error", error.toString());
                                    progressDialog.dismiss();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();
                                    param.put(AppController.GAS_BOOKING_ID, details.getGasBookingId());
                                    return param;
                                }
                            };
                            queue.add(bm);


                        }
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ObjectId = DeliveryBoyList.get(position).getObjectId();
        DeliveryBoy = DeliveryBoyList.get(position).getUsername();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
