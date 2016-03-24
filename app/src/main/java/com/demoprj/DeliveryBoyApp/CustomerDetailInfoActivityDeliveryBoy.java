package com.demoprj.DeliveryBoyApp;

import android.Manifest;
import android.app.Activity;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demoprj.Constant.AppConstant;
import com.demoprj.MapActivity;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.Utils.GPSTracker;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CustomerDetailInfoActivityDeliveryBoy extends AppCompatActivity implements View.OnClickListener {

    TextView lblParticularCustomerId;
    TextView lblParticularCustomerName;
    TextView lblParticularCustomerAddress;
    TextView lblParticularCustomerGasType;
    TextView lblParticularCustomerPhoneNumber;
    TextView lblParticularCustomerLatLng;
    TextView lblParticularCustomerLandmark;
    TextView lblParticularCustomerDOB;
    TextView lblParticularCustomerEmailId;
    TextView lblParticularCustomerSecondaryPhoneNumber;
    TextView lblParticularCustomerGasSize;
    TextView lblParticularCustomerDeliveryCharge;

    ScrollView svParticularCustomerDetail;

    Button btnDirectionDeliveryBoy;
    String gasObjectId;
    int pos = -1;
    //    FloatingActionButton fabEditCustomerDetail;
    GPSTracker gps;
    double currentLatitude;
    double currentLongitude;
    ArrayList<GasSize> gasSizeList = new ArrayList<GasSize>();
    public int CustomerId;
    public String ObjectId;
    int GasTypePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_customer_detail_info_delivery_boy);
        Bundle b = getIntent().getExtras();
        CustomerId = b.getInt("CustomerId");
        ObjectId = b.getString("ObjectId");
        btnDirectionDeliveryBoy = (Button) findViewById(R.id.btnDirectionDeliveryBoy);
        lblParticularCustomerGasSize = (TextView) findViewById(R.id.lblParticularCustomerGasSize);
        lblParticularCustomerDeliveryCharge = (TextView) findViewById(R.id.lblParticularCustomerDeliveryCharge);
        lblParticularCustomerDOB = (TextView) findViewById(R.id.lblParticularCustomerDOB);
        lblParticularCustomerEmailId = (TextView) findViewById(R.id.lblParticularCustomerEmailId);
        lblParticularCustomerSecondaryPhoneNumber = (TextView) findViewById(R.id.lblParticularCustomerSecondaryPhoneNumber);
        lblParticularCustomerId = (TextView) findViewById(R.id.lblParticularCustomerId);
        lblParticularCustomerName = (TextView) findViewById(R.id.lblParticularCustomerName);
        lblParticularCustomerAddress = (TextView) findViewById(R.id.lblParticularCustomerAddress);
        lblParticularCustomerGasType = (TextView) findViewById(R.id.lblParticularCustomerGasType);
        lblParticularCustomerPhoneNumber = (TextView) findViewById(R.id.lblParticularCustomerPhoneNumber);
        lblParticularCustomerLatLng = (TextView) findViewById(R.id.lblParticularCustomerLatLng);
        lblParticularCustomerLandmark = (TextView) findViewById(R.id.lblParticularCustomerLandmark);
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(lblParticularCustomerId.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        svParticularCustomerDetail = (ScrollView) findViewById(R.id.svParticularCustomerDetail);
        lblParticularCustomerPhoneNumber.setOnClickListener(this);
        lblParticularCustomerSecondaryPhoneNumber.setOnClickListener(this);
        lblParticularCustomerPhoneNumber.setPaintFlags(lblParticularCustomerSecondaryPhoneNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnDirectionDeliveryBoy.setOnClickListener(this);
        if (Utils.isInternetAvailable(this)) {
            Utils.showProgressDialog(this, "Fetching User Info");
            ParseQuery<User> particularUserQuery = ParseQuery.getQuery(User.class);
            particularUserQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, CustomerId);
            particularUserQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            final int gasSizePos = objects.get(0).getGasSize();
                            GasType type = (GasType) objects.get(0).get(AppConstant.USER_GAS_TYPE_ID);
                            gasObjectId = type.getObjectId();
                            ParseQuery<GasType> GasTypeQuery = ParseQuery.getQuery(GasType.class);
                            GasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, type.getObjectId());
                            GasTypeQuery.findInBackground(new FindCallback<GasType>() {
                                @Override
                                public void done(List<GasType> objects, ParseException e) {
                                    if (e == null) {
                                        if (objects.size() > 0) {
                                            lblParticularCustomerGasType.setText(objects.get(0).getGasType());
                                            if (objects.get(0).getGasType().equals("Commercial"))
                                                GasTypePosition = 1;
                                            else
                                                GasTypePosition = 0;
                                        }
                                    } else {

                                    }
                                    ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
                                    gasSizeQuery.whereEqualTo(AppConstant.USER_GAS_TYPE_ID, (GasTypePosition + 1));
                                    gasSizeQuery.findInBackground(new FindCallback<GasSize>() {
                                        @Override
                                        public void done(List<GasSize> objects, ParseException e) {
                                            if (e == null) {
                                                if (objects.size() > 0) {
                                                    lblParticularCustomerGasSize.setText(objects.get(gasSizePos).getGasSize() + "");
                                                }
                                            }
                                            Utils.hideProgressDialog();
                                        }
                                    });
                                }
                            });
                            if (objects.get(0).has(AppConstant.USER_DELIVERY_CHARGE)) {
                                lblParticularCustomerDeliveryCharge.setText(objects.get(0).getDeliveryCharge() + "");
                            } else {
                                lblParticularCustomerDeliveryCharge.setText("0");
                            }
                            if (objects.get(0).has(AppConstant.USER_LATLNG)) {
                                if (objects.get(0).getLatLng().equals("")) {
                                    lblParticularCustomerLatLng.setText("No LatLng Available");
                                    btnDirectionDeliveryBoy.setVisibility(View.GONE);
                                } else {
                                    lblParticularCustomerLatLng.setText(objects.get(0).getLatLng());
                                    btnDirectionDeliveryBoy.setVisibility(View.VISIBLE);
                                }
                            } else {
                                lblParticularCustomerLatLng.setText("No LatLng Available");
                                btnDirectionDeliveryBoy.setVisibility(View.GONE);
                            }
                            lblParticularCustomerId.setText(objects.get(0).getCustomerId() + "");
                            lblParticularCustomerLandmark.setText(objects.get(0).getLandmark());
                            lblParticularCustomerName.setText(objects.get(0).getFirstName() + " " + objects.get(0).getMiddleName() + " " + objects.get(0).getLastName());
                            lblParticularCustomerAddress.setText(objects.get(0).getAddress());
                            lblParticularCustomerPhoneNumber.setText(objects.get(0).getphoneNumber() + "");
                            if (objects.get(0).has(AppConstant.USER_EMAILID)) {
                                lblParticularCustomerEmailId.setText(objects.get(0).getEmailId());
                            } else {
                                lblParticularCustomerEmailId.setText("--");
                            }
                            if (objects.get(0).has(AppConstant.USER_SECONDARY_PHONE_NUMBER)) {
                                lblParticularCustomerSecondaryPhoneNumber.setTextColor(Color.BLUE);
                                lblParticularCustomerSecondaryPhoneNumber.setPaintFlags(lblParticularCustomerSecondaryPhoneNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                lblParticularCustomerSecondaryPhoneNumber.setText(objects.get(0).getSecondaryMobileNumber() + "");
                            } else {
                                lblParticularCustomerSecondaryPhoneNumber.setText("--");
                            }
                            if (objects.get(0).has(AppConstant.USER_DOB)) {
                                lblParticularCustomerDOB.setText(objects.get(0).getDOB());
                            } else {
                                lblParticularCustomerDOB.setText("--");
                            }
                        }
                    } else {

                    }
                }
            });
        } else {
            Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblParticularCustomerPhoneNumber:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + lblParticularCustomerPhoneNumber.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(callIntent);
                break;
            case R.id.lblParticularCustomerSecondaryPhoneNumber:
                if (lblParticularCustomerSecondaryPhoneNumber.getText().equals("--")) {

                } else {
                    Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                    callIntent1.setData(Uri.parse("tel:" + lblParticularCustomerSecondaryPhoneNumber.getText().toString()));
                    callIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                    getApplicationContext().startActivity(callIntent1);
                }
                break;
            case R.id.btnDirectionDeliveryBoy:
                gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    currentLatitude = gps.getLatitude();
                    currentLongitude = gps.getLongitude();
                    Intent next = new Intent(this,MapActivity.class);
                    next.putExtra("cLat",currentLatitude);
                    next.putExtra("cLon", currentLongitude);
                    next.putExtra("userLatLng", lblParticularCustomerLatLng.getText().toString());
                    next.putExtra("ObjectId",ParticularCustomerDetailInfo.CustomerId);
                    startActivity(next);
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+currentLatitude+","+currentLongitude+"&daddr="+lblParticularCustomerLatLng.getText().toString()));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                    startActivity(intent);
                }else {
                    gps.showSettingsAlert();
                }
                break;
        }
    }

}
