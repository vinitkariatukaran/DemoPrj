package com.demoprj.DeliveryBoyApp;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demoprj.Constant.AppConstant;
import com.demoprj.MapActivity;
import com.demoprj.MapActivityForDeliveryBoy;
import com.demoprj.ParseModel.Expense;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.SendSms;
import com.demoprj.Utils.GPSTracker;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;

/**
 * Created by Mounil on 07/01/2016.
 */
public class ParticularDeliveryDetailActivityofDeliveryBoy extends AppCompatActivity implements View.OnClickListener {

    TextView lblDBCustomerId;
    TextView lblDBCustomerName;
    TextView lblDBCustomerLandmark;
    TextView lblDBGasType;
    TextView lblDBGasPrice;
    TextView lblDBGasSize;
    TextView lblDBGasQuantity;
    TextView lblDBServiceNote;
    TextView lblDBServiceCharge;
    TextView lblDBCustomerBDate;
    TextView lblDBCustomerDDate;
    TextView lblDBCharge;
    TextView lblDBTotal;
    TextView lblDBCustomerAddress;
    TextView lblDBCustomerLatLng;
    TextView lblDBCustomePhNo;

    LinearLayout llDBDate;
    LinearLayout llDBTypeQuantity;
    LinearLayout llDBSizePrice;
    LinearLayout llDBChargeTotal;
    LinearLayout llDBServiceNote;
    LinearLayout llDBServiceCharge;
    LinearLayout llDBLayout;
    LinearLayout llLatLng;

    String ObjectId;
    String GasObjectId;
    Button btnDBDeliveredLatLng;
    Button btnDBDelivered;
    ArrayList<ParseUser> DeliveryBoyList = new ArrayList<ParseUser>();
    String DeliveryBoy;
    NestedScrollView sv;
    GPSTracker gps;
    public static double currentLatitude;
    public static double currentLongitude;
    int year;
    int month;
    int day;
    Calendar calender;
    String currentDate;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Double TotalAmount = 0.0;
    boolean isService = false;
    Date BookingDate;
    Date SelectedDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_boy_particular_delivery_detail);
        Bundle b = getIntent().getExtras();
        gps = new GPSTracker(this);

        if (b != null) {
            GasObjectId = b.getString("ObjectId");
        }
        llDBDate = (LinearLayout) findViewById(R.id.llDBDate);
        llDBChargeTotal = (LinearLayout) findViewById(R.id.llDBChargeTotal);
        llDBTypeQuantity = (LinearLayout) findViewById(R.id.llDBTypeQuantity);
        llDBSizePrice = (LinearLayout) findViewById(R.id.llDBSizePrice);
        llDBServiceNote = (LinearLayout) findViewById(R.id.llDBServiceNote);
        llLatLng = (LinearLayout) findViewById(R.id.llLatLng);
        llDBServiceCharge = (LinearLayout) findViewById(R.id.llDBServiceCharge);
        llDBLayout = (LinearLayout) findViewById(R.id.llDBLayout);
        sv = (NestedScrollView) findViewById(R.id.sv);
        sv.setBackgroundColor(Color.WHITE);
        btnDBDeliveredLatLng = (Button) findViewById(R.id.btnDBDeliveredLatLng);
        btnDBDelivered = (Button) findViewById(R.id.btnDBDelivered);

        lblDBCustomePhNo = (TextView) findViewById(R.id.lblDBCustomePhNo);
        lblDBCustomerLatLng = (TextView) findViewById(R.id.lblDBCustomerLatLng);
        lblDBCustomerAddress = (TextView) findViewById(R.id.lblDBCustomerAddress);
        lblDBTotal = (TextView) findViewById(R.id.lblDBTotal);
        lblDBCharge = (TextView) findViewById(R.id.lblDBCharge);
        lblDBCustomerBDate = (TextView) findViewById(R.id.lblDBCustomerBDate);
        lblDBCustomerDDate = (TextView) findViewById(R.id.lblDBCustomerDDate);
        lblDBServiceCharge = (TextView) findViewById(R.id.lblDBServiceCharge);
        lblDBServiceNote = (TextView) findViewById(R.id.lblDBServiceNote);
        lblDBCustomerId = (TextView) findViewById(R.id.lblDBCustomerId);
        lblDBCustomerName = (TextView) findViewById(R.id.lblDBCustomerName);
        lblDBCustomerLandmark = (TextView) findViewById(R.id.lblDBCustomerLandmark);
        lblDBGasType = (TextView) findViewById(R.id.lblDBGasType);
        lblDBGasPrice = (TextView) findViewById(R.id.lblDBGasPrice);
        lblDBGasSize = (TextView) findViewById(R.id.lblDBGasSize);
        lblDBGasQuantity = (TextView) findViewById(R.id.lblDBGasQuantity);

        lblDBCustomePhNo.setOnClickListener(this);
        btnDBDeliveredLatLng.setOnClickListener(this);
        btnDBDelivered.setOnClickListener(this);
        llLatLng.setOnClickListener(this);
        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
        currentDate = day + "-" + (month + 1) + "-" + year;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Detail");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (Utils.isInternetAvailable(this)) {
            progressDialog.show();
            ParseQuery<GasBooking> GasQuery = ParseQuery.getQuery(GasBooking.class);
            GasQuery.whereEqualTo(AppConstant.OBJECT_ID, GasObjectId);
            GasQuery.include(AppConstant.GASBOOKING_USER_ID);
            GasQuery.findInBackground(new FindCallback<GasBooking>() {
                @Override
                public void done(List<GasBooking> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            GasBooking gasBooking = objects.get(0);
                            if (gasBooking.getDeliverySatus()) {
                                btnDBDelivered.setVisibility(View.GONE);
                                btnDBDeliveredLatLng.setVisibility(View.GONE);
                            }
                            User u = (User) gasBooking.get(AppConstant.GASBOOKING_USER_ID);
                            lblDBCustomerId.setText(u.getCustomerId() + "");
                            lblDBCustomerName.setText(u.getFirstName() + " " + u.getLastName());
                            lblDBCustomePhNo.setText(u.getphoneNumber() + "");
                            lblDBCustomePhNo.setTextColor(Color.BLUE);
                            lblDBCustomePhNo.setPaintFlags(lblDBCustomePhNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            lblDBCustomerLandmark.setText(u.getLandmark());
                            lblDBCustomerAddress.setText(u.getAddress());
                            if (u.has(AppConstant.USER_LATLNG) && (!u.getLatLng().equals(""))) {
                                lblDBCustomerLatLng.setText(u.getLatLng());
                            } else {
                                lblDBCustomerLatLng.setText("No LatLng");
                            }
                            if (!gasBooking.getisService()) {
                                final GasType gasType = (GasType) u.get(AppConstant.USER_GAS_TYPE_ID);
                                ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
                                gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasType.getObjectId());
                                gasTypeQuery.findInBackground(new FindCallback<GasType>() {
                                    @Override
                                    public void done(List<GasType> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() > 0) {
                                                lblDBGasType.setText(objects.get(0).getGasType());
                                            }
                                        } else {

                                        }
                                    }
                                });
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm aa");
                            if (gasBooking.getDeliverySatus()) {
                                Date DeliveryDate = gasBooking.getDateOfDelivery();
                                BookingDate = gasBooking.getDateOfBooking();
                                SelectedDate = BookingDate;
                                Log.e("Booking & deliverDate", BookingDate + "    :::::    " + DeliveryDate);
                                llDBDate.setVisibility(View.VISIBLE);
                                lblDBCustomerBDate.setText(sdf.format(BookingDate));
                                lblDBCustomerDDate.setText(sdf.format(DeliveryDate));
                            } else {
                                Date BookingDate1 = gasBooking.getDateOfBooking();
                                SelectedDate = BookingDate1;
                                Log.e("Booking ", BookingDate1 + "");
                                lblDBCustomerBDate.setText(sdf.format(BookingDate1));
                                llDBDate.setVisibility(View.GONE);
                            }

                            if (gasBooking.getisService()) {
                                isService = true;
                                llDBServiceCharge.setVisibility(View.VISIBLE);
                                llDBServiceNote.setVisibility(View.VISIBLE);
                                llDBSizePrice.setVisibility(View.GONE);
                                llDBChargeTotal.setVisibility(View.GONE);
                                llDBTypeQuantity.setVisibility(View.GONE);
                                lblDBServiceCharge.setText(gasBooking.getServiceCharge());
                                TotalAmount = Double.parseDouble(gasBooking.getServiceCharge());
                                lblDBServiceNote.setText(gasBooking.getServiceNote());
                            } else {
                                isService = false;
                                llDBServiceCharge.setVisibility(View.GONE);
                                llDBServiceNote.setVisibility(View.GONE);
                                llDBSizePrice.setVisibility(View.VISIBLE);
                                llDBChargeTotal.setVisibility(View.VISIBLE);
                                llDBTypeQuantity.setVisibility(View.VISIBLE);
                                if (gasBooking.has(AppConstant.GASBOOKING_DELIVERY_CHARGE)) {
                                    lblDBGasPrice.setText((gasBooking.getTotal() - (Double.parseDouble(gasBooking.getDeliveryCharge()))) + "");
                                    lblDBCharge.setText(gasBooking.getDeliveryCharge() + "");
                                    lblDBTotal.setText(gasBooking.getTotal() + "");
                                    TotalAmount = gasBooking.getTotal();
                                } else {
                                    lblDBGasPrice.setText(gasBooking.getTotal() + "");
                                    lblDBCharge.setText("0");
                                    lblDBTotal.setText(gasBooking.getTotal() + "");
                                }

                                GasSize gasSize = (GasSize) gasBooking.get(AppConstant.GASBOOKING_GAS_SIZE_ID);
                                if (gasSize != null) {
                                    lblDBGasSize.setText(gasSize.getGasSize() + "");
                                }
                                lblDBGasQuantity.setText(gasBooking.getQuantity() + "");
                            }
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }
            });
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblDBCustomePhNo:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + lblDBCustomePhNo.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            case R.id.llLatLng:
                if(lblDBCustomerLatLng.equals("No LatLng")){
                    Snackbar.make(llDBLayout, "Sorry! User LatLng is not avaliable", Snackbar.LENGTH_LONG).show();
                }else{
                    if (gps.canGetLocation()) {
                        currentLatitude = gps.getLatitude();
                        currentLongitude = gps.getLongitude();
                        Intent next = new Intent(this,MapActivityForDeliveryBoy.class);
                        next.putExtra("cLat",currentLatitude);
                        next.putExtra("cLon", currentLongitude);
                        next.putExtra("userLatLng", lblDBCustomerLatLng.getText().toString());
                        next.putExtra("ObjectId", Integer.parseInt(lblDBCustomerId.getText().toString()));
                        next.putExtra("GasObjectId", GasObjectId);
                        startActivity(next);
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "&daddr=" + lblDBCustomerLatLng.getText().toString()));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                        startActivity(intent);
                    }else{
                        gps.showSettingsAlert();
                    }
                }
                break;
            case R.id.btnDBDelivered:
                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setTitle("Saving Information");
                pDialog.setMessage("Please Wait");
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);
                pDialog.show();
                ParseQuery<GasBooking> gasQuery = ParseQuery.getQuery(GasBooking.class);
                gasQuery.whereEqualTo(AppConstant.OBJECT_ID, GasObjectId);
                gasQuery.findInBackground(new FindCallback<GasBooking>() {
                    @Override
                    public void done(List<GasBooking> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                objects.get(0).setDeliverySatus(true);
                                objects.get(0).setDateOfDelivery(new Date());
                                objects.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Snackbar.make(llDBLayout, "Data Successfully Save", Snackbar.LENGTH_LONG).show();
                                            finish();
                                            if(!isService){
                                                ParseQuery<ParseUser> puQuery = ParseUser.getQuery();
                                                puQuery.whereEqualTo(AppConstant.IS_ADMIN,true);
                                                puQuery.findInBackground(new FindCallback<ParseUser>() {
                                                    @Override
                                                    public void done(List<ParseUser> objects, ParseException e) {
                                                        if (e == null) {
                                                            if (objects.size() > 0) {
                                                                Calendar c = Calendar.getInstance();
                                                                String message = "Welcome to Datar distributor gas agency. Your order of " + simpleDateFormat.format(SelectedDate) + " has been delivered successfully on " + c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ". You have paid Rs. " + lblDBTotal.getText().toString() + "/-. For any queries please contact "+objects.get(0).get(AppConstant.PHONENUMBER)+". Thank you.";
                                                                new SendSms(ParticularDeliveryDetailActivityofDeliveryBoy.this, message, lblDBCustomePhNo.getText().toString()).execute();
                                                            }
                                                        } else {

                                                        }
                                                    }
                                                });
                                            }
                                            SaveDeliveryAmount(pDialog);
                                        } else {

                                        }
                                        pDialog.dismiss();
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
                break;
            case R.id.btnDBDeliveredLatLng:
                if (gps.canGetLocation()) {
                    currentLatitude = gps.getLatitude();
                    currentLongitude = gps.getLongitude();
                    if(currentLatitude != 0.0 && currentLongitude != 0.0){
                        final ProgressDialog pDialog1 = new ProgressDialog(this);
                        pDialog1.setTitle("Saving Information");
                        pDialog1.setMessage("Please Wait");
                        pDialog1.setCanceledOnTouchOutside(false);
                        pDialog1.setCancelable(false);
                        pDialog1.show();
                        ParseQuery<GasBooking> gasQuery1 = ParseQuery.getQuery(GasBooking.class);
                        gasQuery1.whereEqualTo(AppConstant.OBJECT_ID, GasObjectId);
                        gasQuery1.findInBackground(new FindCallback<GasBooking>() {
                            @Override
                            public void done(List<GasBooking> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        objects.get(0).setDeliverySatus(true);
                                        objects.get(0).setDateOfDelivery(new Date());
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Snackbar.make(llDBLayout, "Data Successfully Save", Snackbar.LENGTH_LONG).show();
                                                    if(!isService){
                                                        ParseQuery<ParseUser> puQuery = ParseUser.getQuery();
                                                        puQuery.whereEqualTo(AppConstant.IS_ADMIN,true);
                                                        puQuery.findInBackground(new FindCallback<ParseUser>() {
                                                            @Override
                                                            public void done(List<ParseUser> objects, ParseException e) {
                                                                if (e == null) {
                                                                    if (objects.size() > 0) {
                                                                        Calendar c = Calendar.getInstance();
                                                                        String message = "Welcome to Datar distributor gas agency. Your order of "+simpleDateFormat.format(SelectedDate)+" has been delivered successfully on "+ c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ". You have paid Rs. " + lblDBTotal.getText().toString()+"/-. For any queries please contact " + objects.get(0).get(AppConstant.PHONENUMBER) + ". Thank you.";
                                                                        new SendSms(ParticularDeliveryDetailActivityofDeliveryBoy.this, message, lblDBCustomePhNo.getText().toString()).execute();
                                                                    }
                                                                } else {

                                                                }
                                                            }
                                                        });
                                                    }
                                                    SaveDeliveryAmount(pDialog1);
                                                } else {

                                                }
                                            }
                                        });
                                    }
                                } else {

                                }
                            }
                        });
                        ParseQuery<User> userUpdateQuery = ParseQuery.getQuery(User.class);
                        userUpdateQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID,Integer.parseInt(lblDBCustomerId.getText().toString()));
                        userUpdateQuery.findInBackground(new FindCallback<User>() {
                            @Override
                            public void done(List<User> objects, ParseException e) {
                                if(e==null){
                                    if(objects.size()>0){
                                        objects.get(0).setLatLng(currentLatitude+", "+currentLongitude);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if(e==null){
                                                    Snackbar.make(llDBLayout, "User information Updated successfully", Snackbar.LENGTH_LONG).show();
                                                    finish();
                                                }else{
                                                    Snackbar.make(llDBLayout, "Some error occured. Please try again.", Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }else{
                                    Log.e("Error in update customer Latlng in deliveryBoy",e.getMessage());
                                }
                                pDialog1.dismiss();
                            }
                        });
                    }
                    Log.i("LatLng",currentLatitude+" ----- "+currentLongitude);
                }else {
                    gps.showSettingsAlert();
                }
                break;
        }
    }

    private void SaveDeliveryAmount(final ProgressDialog progressDialog) {
        try {
            final Date selectDate = format.parse(currentDate);
            ParseQuery<Expense> expenseQuery = ParseQuery.getQuery(Expense.class);
            expenseQuery.whereEqualTo(AppConstant.EXPENSE_DATE,selectDate);
            expenseQuery.whereEqualTo(AppConstant.EXPENSE_DELIVERYBOY, ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
            expenseQuery.findInBackground(new FindCallback<Expense>() {
                @Override
                public void done(List<Expense> objects, ParseException e) {
                    if(e==null){
                        if(objects.size()>0){
                            objects.get(0).setDeliveryBoyId(ParseUser.getCurrentUser().getObjectId());
                            objects.get(0).setAmount(0);
                            objects.get(0).setDetail("");
                            objects.get(0).setDate(selectDate);
                            Double amount = objects.get(0).getDeliveryAmount();
                            amount = amount +  TotalAmount;
                            objects.get(0).setDeliveryAmount(amount);
                            objects.get(0).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e==null){

                                    }else{

                                    }
                                    progressDialog.dismiss();
                                }
                            });
                        }else{
                            Expense expense = new Expense();
                            expense.setDetail("");
                            expense.setDate(selectDate);
                            expense.setAmount(0);
                            expense.setDeliveryBoyId(ParseUser.getCurrentUser().getObjectId());
                            expense.setDeliveryAmount(TotalAmount);
                            expense.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e==null){
                                    }else{

                                    }
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }else{
                        progressDialog.dismiss();
                    }
                }
            });
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}
