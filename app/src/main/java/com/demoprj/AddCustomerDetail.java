package com.demoprj;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCustomerDetail extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {

    TextInputLayout inputAddCustomerId;
    TextInputLayout inputAddFirstName;
    TextInputLayout inputAddMiddleName;
    TextInputLayout inputAddLastName;
    TextInputLayout inputAddAddress;
    TextInputLayout inputAddPhoneNumber;
    TextInputLayout inputAddLandmark;
    TextInputLayout inputAddSecondaryNumber;
    TextInputLayout inputAddEmail;
    TextInputLayout inputAddDOB;
    TextInputLayout inputAddDeliveryCharge;

    EditText txtAddCustomerId;
    EditText txtAddFirstName;
    EditText txtAddMiddleName;
    EditText txtAddLastName;
    EditText txtAddAddress;
    EditText txtAddPhoneNumber;
    EditText txtAddLandmark;
    EditText txtAddEmail;
    EditText txtAddSecondaryNumber;
    EditText txtAddDOB;
    EditText txtAddDeliveryCharge;

    Spinner spnAddGasSize;
    Button btnAddSave;

    RadioGroup rgAddGasType;
    RadioButton rbAddGasCommercial;
    RadioButton rbSelectedGasType;

    ImageButton ibAddDOB;
    ScrollView svAddCustomer;
    int pos = 0;
    RequestQueue queue;
    ProgressDialog pDialog;

    String GasTypeObjectId;
    boolean Domestic = true;
    int year;
    int month;
    int day;
    Calendar c;
    int gasTypePosition = 0;
    ArrayList<GasSize> gasSizeList = new ArrayList<GasSize>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_detail);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Saving Information");
        pDialog.setMessage("Please wait a Minute");
        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress1));
        svAddCustomer = (ScrollView) findViewById(R.id.svAddCustomer);

        inputAddDeliveryCharge = (TextInputLayout) findViewById(R.id.inputAddDeliveryCharge);
        inputAddCustomerId = (TextInputLayout) findViewById(R.id.inputAddCustomerId);
        inputAddFirstName = (TextInputLayout) findViewById(R.id.inputAddFirstName);
        inputAddMiddleName = (TextInputLayout) findViewById(R.id.inputAddMiddleName);
        inputAddLastName = (TextInputLayout) findViewById(R.id.inputAddLastName);
        inputAddAddress = (TextInputLayout) findViewById(R.id.inputAddAddress);
        inputAddPhoneNumber = (TextInputLayout) findViewById(R.id.inputAddPhoneNumber);
        inputAddLandmark = (TextInputLayout) findViewById(R.id.inputAddLandmark);
        inputAddSecondaryNumber = (TextInputLayout) findViewById(R.id.inputAddSecondaryNumber);
        inputAddEmail = (TextInputLayout) findViewById(R.id.inputAddEmail);
        inputAddDOB = (TextInputLayout) findViewById(R.id.inputAddDOB);

        txtAddDOB = (EditText) findViewById(R.id.txtAddDOB);
        txtAddSecondaryNumber = (EditText) findViewById(R.id.txtAddSecondaryNumber);
        txtAddEmail = (EditText) findViewById(R.id.txtAddEmail);
        txtAddCustomerId = (EditText) findViewById(R.id.txtAddCustomerId);
        txtAddFirstName = (EditText) findViewById(R.id.txtAddFirstName);
        txtAddMiddleName = (EditText) findViewById(R.id.txtAddMiddleName);
        txtAddLastName = (EditText) findViewById(R.id.txtAddLastName);
        txtAddAddress = (EditText) findViewById(R.id.txtAddAddress);
        txtAddPhoneNumber = (EditText) findViewById(R.id.txtAddPhoneNumber);
        txtAddLandmark = (EditText) findViewById(R.id.txtAddLandmark);
        txtAddDeliveryCharge = (EditText) findViewById(R.id.txtAddDeliveryCharge);

        ibAddDOB = (ImageButton) findViewById(R.id.ibAddDOB);

        spnAddGasSize = (Spinner) findViewById(R.id.spnAddGasSize);

        rgAddGasType = (RadioGroup) findViewById(R.id.rgAddGasType);
        rbAddGasCommercial = (RadioButton) findViewById(R.id.rbAddGasCommercial);
        rbSelectedGasType = (RadioButton) findViewById(rgAddGasType.getCheckedRadioButtonId());

        rgAddGasType.setOnCheckedChangeListener(this);
        btnAddSave = (Button) findViewById(R.id.btnAddSave);

        btnAddSave.setOnClickListener(this);
        ibAddDOB.setOnClickListener(this);
        SettingGasSize(1);
        spnAddGasSize.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddDOB:

                DatePickerDialog dpDialog = new DatePickerDialog(this, this, year, month, day);
                dpDialog.getDatePicker().setCalendarViewShown(false);
                dpDialog.getDatePicker().setSpinnersShown(true);
                dpDialog.getDatePicker().setMaxDate(new Date().getTime());
                dpDialog.show();
                break;
            case R.id.btnAddSave:
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (txtAddCustomerId.getText() != null && txtAddCustomerId.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(true);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddCustomerId.setError(getResources().getString(R.string.Field_require));
                    txtAddCustomerId.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Customer Id", Snackbar.LENGTH_LONG).show();
                } else if (txtAddFirstName.getText() != null && txtAddFirstName.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(true);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddFirstName.setError(getResources().getString(R.string.Field_require));
                    txtAddFirstName.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter First Name", Snackbar.LENGTH_LONG).show();
                } else if (txtAddMiddleName.getText() != null && txtAddMiddleName.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(true);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddMiddleName.setError(getResources().getString(R.string.Field_require));
                    txtAddMiddleName.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Middle Name", Snackbar.LENGTH_LONG).show();
                } else if (txtAddLastName.getText() != null && txtAddLastName.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(true);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddLastName.setError(getResources().getString(R.string.Field_require));
                    txtAddLastName.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Last Name", Snackbar.LENGTH_LONG).show();
                } else if (txtAddAddress.getText() != null && txtAddAddress.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(true);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddAddress.setError(getResources().getString(R.string.Field_require));
                    txtAddAddress.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Address", Snackbar.LENGTH_LONG).show();
                } else if (txtAddLandmark.getText() != null && txtAddLandmark.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(true);
                    inputAddLandmark.setError(getResources().getString(R.string.Field_require));
                    txtAddLandmark.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Address", Snackbar.LENGTH_LONG).show();
                } else if (txtAddPhoneNumber.getText() != null && txtAddPhoneNumber.getText().toString().length() <= 0) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(true);
                    inputAddPhoneNumber.setError(getResources().getString(R.string.Field_require));
                    txtAddPhoneNumber.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter Phone Number", Snackbar.LENGTH_LONG).show();
                } else if (txtAddPhoneNumber.getText() != null && txtAddPhoneNumber.getText().toString().length() != 10) {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(true);
                    inputAddLandmark.setErrorEnabled(false);
                    inputAddPhoneNumber.setError(getResources().getString(R.string.Field_require));
                    txtAddPhoneNumber.requestFocus();
                    Snackbar.make(svAddCustomer, "Please enter 10 digit Phone Number", Snackbar.LENGTH_LONG).show();
                } else {
                    inputAddCustomerId.setErrorEnabled(false);
                    inputAddFirstName.setErrorEnabled(false);
                    inputAddMiddleName.setErrorEnabled(false);
                    inputAddLastName.setErrorEnabled(false);
                    inputAddAddress.setErrorEnabled(false);
                    inputAddPhoneNumber.setErrorEnabled(false);
                    inputAddLandmark.setErrorEnabled(false);
                    if (Utils.isInternetAvailable(this)) {
                        pDialog.show();
                        queue = CustomVolleyRequestQueue.getInstance(getApplicationContext())
                                .getRequestQueue();
                        JSONObject jObject = new JSONObject();
                        String url = AppController.mainUrl+AppController.ADDCUSTOMER;
//                        try {
//                            jObject.put(AppController.FIRSTNAME, txtAddFirstName.getText().toString());
//                            jObject.put(AppController.MIDDLENAME,txtAddMiddleName.getText().toString());
//                            jObject.put(AppController.LASTNAME,txtAddLastName.getText().toString());
//                            jObject.put(AppController.ADDRESS,txtAddAddress.getText().toString());
//                            jObject.put(AppController.LANDMARK,txtAddLandmark.getText().toString());
//                            jObject.put(AppController.PHONE_NUMBER,Long.parseLong(txtAddPhoneNumber.getText().toString()));
////                                                jObject.setCustomerId(AppController.FIRSTNAME,Integer.parseInt(txtAddCustomerId.getText().toString()));
//                            jObject.put(AppController.GAS_TYPE_ID,1);
//                            jObject.put(AppController.GAS_SIZE_ID,1);
//                            if(txtAddDeliveryCharge.getText()==null || txtAddDeliveryCharge.getText().toString().length()<=0)
//                                jObject.put(AppController.DELIVERY_CHARGE,0);
//                            else
//                                jObject.put(AppController.DELIVERY_CHARGE,Integer.parseInt(txtAddDeliveryCharge.getText().toString()));
//                            if (txtAddEmail.getText() != null && txtAddEmail.getText().toString().length() > 0)
//                                jObject.put(AppController.EMAIL,txtAddEmail.getText().toString());
//                            if (txtAddSecondaryNumber.getText() != null && txtAddSecondaryNumber.getText().toString().length() > 0)
//                                jObject.put(AppController.SECOND_MOBILE_NUMBER,Long.parseLong(txtAddSecondaryNumber.getText().toString()));
//                            if (txtAddDOB.getText() != null && txtAddDOB.getText().toString().length() > 0)
//                                jObject.put(AppController.DOB,txtAddDOB.getText().toString());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response ",response);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("response error",error.toString());
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();
                                    param.put(AppController.FIRSTNAME, txtAddFirstName.getText().toString());
                                    param.put(AppController.MIDDLENAME,txtAddMiddleName.getText().toString());
                                    param.put(AppController.LASTNAME,txtAddLastName.getText().toString());
                                    param.put(AppController.ADDRESS,txtAddAddress.getText().toString());
                                    param.put(AppController.LANDMARK,txtAddLandmark.getText().toString());
                                    param.put(AppController.PHONE_NUMBER,txtAddPhoneNumber.getText().toString());
                                    param.put(AppController.CUSTOMERID, txtAddCustomerId.getText().toString());
                                    param.put(AppController.GAS_TYPE_ID,"1");
                                    param.put(AppController.GAS_SIZE_ID,"1");
                                    param.put(AppController.GB_USER_ID,"2");
                                    if(txtAddDeliveryCharge.getText()==null || txtAddDeliveryCharge.getText().toString().length()<=0)
                                        param.put(AppController.DELIVERY_CHARGE,"0");
                                    else
                                        param.put(AppController.DELIVERY_CHARGE,txtAddDeliveryCharge.getText().toString());
                                    if (txtAddEmail.getText() != null && txtAddEmail.getText().toString().length() > 0)
                                        param.put(AppController.EMAIL,txtAddEmail.getText().toString());
                                    if (txtAddSecondaryNumber.getText() != null && txtAddSecondaryNumber.getText().toString().length() > 0)
                                        param.put(AppController.SECOND_MOBILE_NUMBER,txtAddSecondaryNumber.getText().toString());
                                    if (txtAddDOB.getText() != null && txtAddDOB.getText().toString().length() > 0) {
                                        String[] date = txtAddDOB.getText().toString().split("/");
                                        if(date[0].length()<=1)
                                            date[0] = "0"+date[0];
                                        if(date[1].length()<=1)
                                            date[1] = "0"+date[1];
                                        param.put(AppController.DOB, date[2]+"-"+date[1]+"-"+date[0]);
                                    }
                                    return param;
                                }
                            };
                            /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    pDialog.dismiss();
                                    Log.e("Response",response.toString());
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.dismiss();
                                    Log.e("Response error", error.toString());
                                }
                            });*/
                            queue.add(stringRequest);
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
                        ParseQuery<User> UserQuery = ParseQuery.getQuery(User.class);
                        UserQuery.addDescendingOrder(AppConstant.USER_CUSTOMER_ID);
                        UserQuery.findInBackground(new FindCallback<User>() {
                            @Override
                            public void done(List<User> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        int cId = objects.get(0).getCustomerId();
//                                        savingInformation(cId);
                                    } else {
//                                        savingInformation(99);
                                    }
                                } else {

                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void savingInformation(final int cId) {
        if (Utils.isInternetAvailable(this)) {
            ParseQuery<User> CheckCustomerIdQuery = ParseQuery.getQuery(User.class);
            CheckCustomerIdQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, Integer.parseInt(txtAddCustomerId.getText().toString()));
            CheckCustomerIdQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            pDialog.dismiss();
                            AlertDialog.Builder aDialog = new AlertDialog.Builder(AddCustomerDetail.this);
                            aDialog.setTitle("Customer Id alredy Exists");
                            aDialog.setMessage("Customer Id " + txtAddCustomerId.getText().toString() + " belongs to " + objects.get(0).getFirstName() + " " + objects.get(0).getLastName());
                            aDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                            aDialog.show();
                        } else {
                            ParseQuery<GasType> gasType = ParseQuery.getQuery(GasType.class);


                            gasType.findInBackground(new FindCallback<GasType>() {
                                @Override
                                public void done(List<GasType> objects, ParseException e) {
                                    if (e == null) {
                                        if (objects.size() > 0) {

                                            User savingCustomerDetail = new User();
                                            savingCustomerDetail.setFirstName(txtAddFirstName.getText().toString());
                                            savingCustomerDetail.setMiddleName(txtAddMiddleName.getText().toString());
                                            savingCustomerDetail.setLastName(txtAddLastName.getText().toString());
                                            savingCustomerDetail.setAddress(txtAddAddress.getText().toString());
                                            savingCustomerDetail.setLandmark(txtAddLandmark.getText().toString());
                                            savingCustomerDetail.setphoneNumber(Long.parseLong(txtAddPhoneNumber.getText().toString()));
                                            savingCustomerDetail.setCustomerId(Integer.parseInt(txtAddCustomerId.getText().toString()));
                                            savingCustomerDetail.setGasTypeId(objects.get(gasTypePosition).getObjectId());
                                            savingCustomerDetail.setGasSizeId(pos);
                                            if(txtAddDeliveryCharge.getText()==null || txtAddDeliveryCharge.getText().toString().length()<=0)
                                                savingCustomerDetail.setDeliveryCharge(0);
                                            else
                                                savingCustomerDetail.setDeliveryCharge(Integer.parseInt(txtAddDeliveryCharge.getText().toString()));
                                            if (txtAddEmail.getText() != null && txtAddEmail.getText().toString().length() > 0)
                                                savingCustomerDetail.setEmailId(txtAddEmail.getText().toString());
                                            if (txtAddSecondaryNumber.getText() != null && txtAddSecondaryNumber.getText().toString().length() > 0)
                                                savingCustomerDetail.setSecondaryMobileNumber(Long.parseLong(txtAddSecondaryNumber.getText().toString()));
                                            if (txtAddDOB.getText() != null && txtAddDOB.getText().toString().length() > 0)
                                                savingCustomerDetail.setDOB(txtAddDOB.getText().toString());
                                            savingCustomerDetail.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    pDialog.dismiss();
                                                    if (e == null) {
                                                        AlertDialog.Builder aDialog = new AlertDialog.Builder(AddCustomerDetail.this);
                                                        aDialog.setTitle("Customer Detail Save Successfully");
                                                        aDialog.setMessage("Customer Id is " + txtAddCustomerId.getText().toString());
                                                        aDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                                clearData();
                                                            }
                                                        });
                                                        aDialog.show();
                                                    } else {

                                                    }

                                                }
                                            });
                                        }
                                    } else {

                                    }
                                }
                            });

                        }
                    } else {

                    }
                }
            });
        } else {
            Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    private void clearData() {
        txtAddCustomerId.setText("");
        txtAddLandmark.setText("");
        txtAddFirstName.setText("");
        txtAddMiddleName.setText("");
        txtAddLastName.setText("");
        txtAddAddress.setText("");
        txtAddPhoneNumber.setText("");
        spnAddGasSize.setSelection(0);
        txtAddCustomerId.requestFocus();

//        Vinit K
        txtAddSecondaryNumber.setText("");
        txtAddEmail.setText("");
        txtAddDOB.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        txtAddDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rbAddGasCommercial:
                Domestic = false;
                SettingGasSize(2);
                gasTypePosition = 1;
                break;
            case R.id.rbAddGasDomestic:
                Domestic = true;
                SettingGasSize(1);
                gasTypePosition = 0;
                break;
        }
    }
    private void SettingGasSize(int GasTypeId) {
        if(Utils.isInternetAvailable(this)) {
            ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCustomerDetail.this, android.R.layout.simple_list_item_1, Gas);
                            spnAddGasSize.setAdapter(adapter);
                            if(Domestic)
                                spnAddGasSize.setSelection(3);
                            else
                                spnAddGasSize.setSelection(1);
                        }
                    }
                }
            });
        }else {
            Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }
}
