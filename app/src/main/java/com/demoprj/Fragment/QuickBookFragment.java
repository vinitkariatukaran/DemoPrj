package com.demoprj.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.GsonModel.CustomerDetail;
import com.demoprj.GsonModel.DeliverdBottelDetail;
import com.demoprj.GsonModel.UndeliveryDetail;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.R;
import com.demoprj.SendSms;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickBookFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    TextInputLayout inputAdminBookCustomerNumber;
    TextInputLayout inputAdminBookCustomerQuantity;
    TextInputLayout inputAdminBookDeliveryCharge;
    TextInputLayout inputAdminBookServiceNote;
    TextInputLayout inputAdminBookServiceCharge;

    EditText txtAdminBookCustomerNumber;
    EditText txtAdminBookCustomerQuantity;
    EditText txtAdminBookDeliveryCharge;
    EditText txtAdminBookServiceNote;
    EditText txtAdminBookServiceCharge;

    CheckBox chbService;

    RadioGroup rgAdminBookType;
    RadioButton selectedType;
    Spinner spnAdminBookGasSize;
    Spinner spnAdminBookDeliveryBoy;

    TextView lblAdminBookTotal;
    Button btnAdminBook;
    Button btnBookDone;
    NestedScrollView svQuickBook;
    boolean fromWhere = false;

    ArrayList<GasSize> gasSizeList = new ArrayList<GasSize>();

    TextView lblADName;
    TextView lblADAddress;
    TextView lblADServiceNote;
    TextView lblADServiceCharge;
    TextView lblADPhoneNumber;
    TextView lblADQuantity;
    TextView lblADTotal;
    LinearLayout llADQuantity;
    LinearLayout llADTotal;
    LinearLayout llNote;
    LinearLayout llCharge;
    double Price=1;
    User SelectedUser;
    RadioButton rbAdminBookDomestic;
    RadioButton rbAdminBookCommercial;
    ProgressDialog progressDialog;
    String DeliveryBoyId = "";
    ArrayList<ParseUser> DeliveryBoyList = new ArrayList<ParseUser>();
    LinearLayout llType;
    LinearLayout lblBookTotal;
    TextView lblBookGasSize;
    boolean newService = false;
    boolean Domestic = true;
    ViewGroup mainContainer;
    InputMethodManager imm;
    ArrayList<GasType> gasTypeList = new ArrayList<>();
    int gasTypePosition;
    RequestQueue queue;
    @Override
    public void onStart() {
        super.onStart();
        if(mainContainer!=null && imm!=null)
            imm.hideSoftInputFromWindow(mainContainer.getWindowToken(), 0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quick_book, container, false);
        mainContainer = container;
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait");
        llType = (LinearLayout) rootView.findViewById(R.id.llType);
        lblBookTotal = (LinearLayout) rootView.findViewById(R.id.lblBookTotal);
        inputAdminBookCustomerNumber = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookCustomerNumber);
        lblBookGasSize = (TextView) rootView.findViewById(R.id.lblBookGasSize);
        inputAdminBookCustomerQuantity = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookQuantity);
        inputAdminBookCustomerQuantity = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookQuantity);
        inputAdminBookDeliveryCharge = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookDeliveryCharge);
        inputAdminBookServiceCharge = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookServiceCharge);
        inputAdminBookServiceNote = (TextInputLayout) rootView.findViewById(R.id.inputAdminBookServiceNote);

        txtAdminBookCustomerNumber = (EditText) rootView.findViewById(R.id.txtAdminBookCustomerNumber);
        txtAdminBookCustomerQuantity = (EditText) rootView.findViewById(R.id.txtAdminBookQuantity);
        txtAdminBookDeliveryCharge = (EditText) rootView.findViewById(R.id.txtAdminBookDeliveryCharge);
        txtAdminBookServiceNote = (EditText) rootView.findViewById(R.id.txtAdminBookServiceNote);
        txtAdminBookServiceCharge = (EditText) rootView.findViewById(R.id.txtAdminBookServiceCharge);

        chbService = (CheckBox) rootView.findViewById(R.id.chbService);

        rgAdminBookType = (RadioGroup) rootView.findViewById(R.id.rgAdminBookType);
        selectedType = (RadioButton) rgAdminBookType.findViewById(rgAdminBookType.getCheckedRadioButtonId());
        rbAdminBookDomestic = (RadioButton) rootView.findViewById(R.id.rbAdminBookDomestic);
        rbAdminBookCommercial = (RadioButton) rootView.findViewById(R.id.rbAdminBookCommercial);
        spnAdminBookGasSize = (Spinner) rootView.findViewById(R.id.spnAdminBookGasSize);
        spnAdminBookDeliveryBoy = (Spinner) rootView.findViewById(R.id.spnAdminBookDeliveryBoy);

        lblAdminBookTotal = (TextView) rootView.findViewById(R.id.lblAdminBookTotal);
        btnAdminBook = (Button) rootView.findViewById(R.id.btnAdminBook);
        btnBookDone = (Button) rootView.findViewById(R.id.btnBookDone);

        svQuickBook = (NestedScrollView) rootView.findViewById(R.id.svQuickBook);
        spnAdminBookDeliveryBoy.setVisibility(View.GONE);
        spnAdminBookGasSize.setVisibility(View.GONE);
        inputAdminBookDeliveryCharge.setVisibility(View.GONE);
        inputAdminBookCustomerQuantity.setVisibility(View.GONE);
        lblBookGasSize.setVisibility(View.GONE);
        llType.setVisibility(View.GONE);
        lblBookTotal.setVisibility(View.GONE);
        chbService.setVisibility(View.GONE);
        btnAdminBook.setVisibility(View.GONE);
        btnAdminBook.setOnClickListener(this);
        btnBookDone.setOnClickListener(this);
//        String url = AppController.mainUrl+AppController.API_GetCustomer;
        String url = AppController.mainUrl+AppController.API_DELIVERED_BOTTLE;

        RequestQueue queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        baseModel bm = new baseModel(Request.Method.GET,url,DeliverdBottelDetail.class, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.e("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response error",error.toString());
            }
        });

        queue.add(bm);
        if(Utils.isInternetAvailable(getActivity())) {
            ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
            gasTypeQuery.findInBackground(new FindCallback<GasType>() {
                @Override
                public void done(List<GasType> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            gasTypeList.addAll(objects);
                        }
                    }
                }
            });
        }
//        if(!fromWhere)
//            SettingGasSize(1,1);
        settingDeliveryBoy();
        spnAdminBookGasSize.setOnItemSelectedListener(this);
        spnAdminBookDeliveryBoy.setOnItemSelectedListener(this);
        chbService.setOnCheckedChangeListener(this);
        rgAdminBookType.setOnCheckedChangeListener(this);
        txtAdminBookCustomerQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && (!s.equals("")) && s.length() > 0) {
//                    double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
//                    lblAdminBookTotal.setText(amount + "");
                    if(txtAdminBookDeliveryCharge.getText() != null && txtAdminBookDeliveryCharge.getText().toString().length()>0 && txtAdminBookCustomerQuantity.getText() != null && txtAdminBookCustomerQuantity.getText().toString().length()>0) {
                        double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
                        lblAdminBookTotal.setText( (amount + Integer.parseInt(txtAdminBookDeliveryCharge.getText().toString())) + "");
                    }else {
                        if(txtAdminBookCustomerQuantity.getText() != null && txtAdminBookCustomerQuantity.getText().toString().length()>0 && !txtAdminBookCustomerQuantity.getText().toString().equals("0")) {
                            double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
                            lblAdminBookTotal.setText(amount + "");
                        }else
                            lblAdminBookTotal.setText("0");
                    }
                } else {
                    lblAdminBookTotal.setText("0");
                }
            }
        });

        txtAdminBookDeliveryCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!txtAdminBookDeliveryCharge.getText().toString().equals("0.0")) {
                    if (txtAdminBookDeliveryCharge.getText() != null && txtAdminBookDeliveryCharge.getText().toString().length() > 0 && txtAdminBookCustomerQuantity.getText() != null && txtAdminBookCustomerQuantity.getText().toString().length() > 0) {
                        double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
                        lblAdminBookTotal.setText((amount + Integer.parseInt(txtAdminBookDeliveryCharge.getText().toString())) + "");
                    } else {
                        if (txtAdminBookCustomerQuantity.getText() != null && txtAdminBookCustomerQuantity.getText().toString().length() > 0 && !txtAdminBookCustomerQuantity.getText().toString().equals("0")) {
                            double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
                            lblAdminBookTotal.setText(amount + "");
                        } else
                            lblAdminBookTotal.setText("0");
                    }
                }
            }
        });
        return rootView;
    }

    private void settingDeliveryBoy() {
        if(Utils.isInternetAvailable(getActivity())) {
            ParseQuery<ParseUser> deliveryBotQuery = ParseQuery.getQuery(ParseUser.class);
            deliveryBotQuery.whereEqualTo(AppConstant.IS_ADMIN, false);
            deliveryBotQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            DeliveryBoyList.addAll(objects);
                            ArrayList<String> DeliveryBoy = new ArrayList<String>();
                            for (int i = 0; i < objects.size(); i++) {
                                DeliveryBoy.add(objects.get(i).getUsername());
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, DeliveryBoy);
                            spnAdminBookDeliveryBoy.setAdapter(adapter);
                        }
                    } else {

                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    private void SettingGasSize(int GasTypeId, final int showProgress) {
        if(Utils.isInternetAvailable(getActivity())) {
            if(showProgress==1) {
                progressDialog.setTitle("Fetching Detail");
                progressDialog.show();
            }
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Gas);
                            spnAdminBookGasSize.setAdapter(adapter);
                            if (Domestic)
                                spnAdminBookGasSize.setSelection(3);
                            else
                                spnAdminBookGasSize.setSelection(1);
                        }
                        if (showProgress == 1)
                            if (progressDialog != null)
                                progressDialog.hide();
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBookDone:
                if(txtAdminBookCustomerNumber.getText()!=null && txtAdminBookCustomerNumber.getText().toString().length()>0) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (Utils.isInternetAvailable(getActivity())) {
                        final ProgressDialog pDialog = new ProgressDialog(getActivity());
                        pDialog.setTitle("Fetching User Info");
                        pDialog.setMessage("Please Wait");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
                        userQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, Integer.parseInt(txtAdminBookCustomerNumber.getText().toString()));
                        userQuery.findInBackground(new FindCallback<User>() {
                            @Override
                            public void done(List<User> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        final User selectedUser = objects.get(0);
                                        GasType gt = (GasType) selectedUser.get(AppConstant.USER_GAS_TYPE_ID);
                                        if (objects.get(0).has(AppConstant.USER_DELIVERY_CHARGE)) {
                                            if (objects.get(0).getDeliveryCharge() != 0) {
                                                txtAdminBookDeliveryCharge.setText(selectedUser.getDeliveryCharge() + "");
                                            } else {
                                                txtAdminBookDeliveryCharge.setText("0");
                                            }
                                        } else {
                                            txtAdminBookDeliveryCharge.setText("0");
                                        }
                                        ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
                                        gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gt.getObjectId());
                                        gasTypeQuery.findInBackground(new FindCallback<GasType>() {
                                            @Override
                                            public void done(List<GasType> objects, ParseException e) {
                                                if (e == null) {
                                                    int pos = 0;
                                                    if (objects.size() > 0) {
                                                        if (rbAdminBookCommercial.getText().toString().toLowerCase().equals(objects.get(0).getGasType().toLowerCase())) {
                                                            rbAdminBookCommercial.setChecked(true);
                                                            gasTypePosition = 1;
                                                            pos = 2;
                                                        } else if (rbAdminBookDomestic.getText().toString().toLowerCase().equals(objects.get(0).getGasType().toLowerCase())) {
                                                            rbAdminBookDomestic.setChecked(true);
                                                            gasTypePosition = 0;
                                                            pos = 1;
                                                        }
                                                        ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
                                                        gasSizeQuery.whereEqualTo(AppConstant.USER_GAS_TYPE_ID, pos);
                                                        gasSizeQuery.findInBackground(new FindCallback<GasSize>() {
                                                            @Override
                                                            public void done(List<GasSize> objects, ParseException e) {
                                                                if (e == null) {
                                                                    if (objects.size() > 0) {
                                                                        if (gasSizeList.size() > 0) {
                                                                            gasSizeList.clear();
                                                                        }
                                                                        gasSizeList.addAll(objects);
                                                                        String[] Gas = new String[objects.size()];
                                                                        for (int i = 0; i < objects.size(); i++) {
                                                                            Gas[i] = String.valueOf(objects.get(i).getGasSize());
                                                                        }
                                                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Gas);
                                                                        spnAdminBookGasSize.setAdapter(adapter);
                                                                        spnAdminBookGasSize.setSelection(selectedUser.getGasSize());
                                                                    }
                                                                }

                                                                spnAdminBookDeliveryBoy.setVisibility(View.VISIBLE);
                                                                spnAdminBookGasSize.setVisibility(View.VISIBLE);
                                                                inputAdminBookDeliveryCharge.setVisibility(View.VISIBLE);
                                                                inputAdminBookCustomerQuantity.setVisibility(View.VISIBLE);
                                                                lblBookGasSize.setVisibility(View.VISIBLE);
                                                                llType.setVisibility(View.VISIBLE);
                                                                lblBookTotal.setVisibility(View.VISIBLE);
                                                                chbService.setVisibility(View.VISIBLE);
                                                                btnAdminBook.setVisibility(View.VISIBLE);
                                                            }
                                                        });
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                                    } else {
                                        Snackbar.make(svQuickBook, "Please enter correct Cutomer Number", Snackbar.LENGTH_LONG).show();
                                        spnAdminBookDeliveryBoy.setVisibility(View.GONE);
                                        spnAdminBookGasSize.setVisibility(View.GONE);
                                        inputAdminBookDeliveryCharge.setVisibility(View.GONE);
                                        inputAdminBookCustomerQuantity.setVisibility(View.GONE);
                                        lblBookGasSize.setVisibility(View.GONE);
                                        llType.setVisibility(View.GONE);
                                        lblBookTotal.setVisibility(View.GONE);
                                        chbService.setVisibility(View.GONE);
                                        btnAdminBook.setVisibility(View.GONE);
                                    }
                                }
                                pDialog.dismiss();
                            }
                        });
                    }
                }else{
                    Snackbar.make(svQuickBook, "Please enter Cutomer Number", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btnAdminBook:
                btnAdminBook.setEnabled(false);
                if(txtAdminBookCustomerNumber.getText()==null || txtAdminBookCustomerNumber.getText().length()<=0){
                    Snackbar.make(svQuickBook, "Please enter Cutomer Number", Snackbar.LENGTH_LONG).show();
                    txtAdminBookCustomerNumber.requestFocus();
                    inputAdminBookCustomerNumber.setError(getResources().getString(R.string.Field_require));
                    inputAdminBookCustomerNumber.setErrorEnabled(true);
                    btnAdminBook.setEnabled(true);
                }else if(txtAdminBookCustomerQuantity.getText()==null || txtAdminBookCustomerQuantity.getText().length()<=0 || txtAdminBookCustomerQuantity.getText().equals("0")){
                    Snackbar.make(svQuickBook, "Please enter Quantity", Snackbar.LENGTH_LONG).show();
                    txtAdminBookCustomerQuantity.requestFocus();
                    inputAdminBookCustomerQuantity.setError(getResources().getString(R.string.Field_require));
                    inputAdminBookCustomerQuantity.setErrorEnabled(true);
                    btnAdminBook.setEnabled(true);
                }else if(newService){
                    if(txtAdminBookServiceCharge.getText()==null || txtAdminBookServiceCharge.getText().length()<=0){
                        Snackbar.make(svQuickBook, "Please enter Service Charge", Snackbar.LENGTH_LONG).show();
                        txtAdminBookServiceCharge.requestFocus();
                        inputAdminBookServiceCharge.setError(getResources().getString(R.string.Field_require));
                        inputAdminBookServiceCharge.setErrorEnabled(true);
                        inputAdminBookServiceNote.setErrorEnabled(false);
                        btnAdminBook.setEnabled(true);
                    }else if(txtAdminBookServiceNote.getText()==null || txtAdminBookServiceNote.getText().length()<=0){
                        Snackbar.make(svQuickBook, "Please enter Service Note", Snackbar.LENGTH_LONG).show();
                        txtAdminBookServiceNote.requestFocus();
                        inputAdminBookServiceNote.setError(getResources().getString(R.string.Field_require));
                        inputAdminBookServiceNote.setErrorEnabled(true);
                        inputAdminBookServiceCharge.setErrorEnabled(false);
                        btnAdminBook.setEnabled(true);
                    }else{
                        DisplayAlertDialog();
                    }
                }else{
                    DisplayAlertDialog();
                }
                break;
        }
    }

    private void DisplayAlertDialog() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        progressDialog.setTitle("Fetching UserInfo");
        if(Utils.isInternetAvailable(getActivity())) {
            progressDialog.show();
            ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
            userQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, Integer.parseInt(txtAdminBookCustomerNumber.getText().toString()));
            userQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            DisplayUserInfo(objects.get(0));
                            SelectedUser = objects.get(0);

                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setTitle("No User Found");
                            dialog.setMessage("Sorry! No User found on " + txtAdminBookCustomerNumber.getText().toString() + " Customer Number");
                            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        btnAdminBook.setEnabled(true);
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
        fromWhere = true;
    }

    private void DisplayUserInfo(User user) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ad_display_user_info_on_book_click, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.setTitle("Are you sure want to Book Gas Bottle");
        lblADAddress = (TextView) dialogView.findViewById(R.id.lblADAddress);
        lblADName = (TextView) dialogView.findViewById(R.id.lblADName);
        lblADPhoneNumber= (TextView) dialogView.findViewById(R.id.lblADPhoneNumber);
        lblADQuantity = (TextView) dialogView.findViewById(R.id.lblADQuantity);
        lblADServiceNote = (TextView) dialogView.findViewById(R.id.lblADServiceNote);
        lblADServiceCharge = (TextView) dialogView.findViewById(R.id.lblADServiceCharge);
        llADQuantity = (LinearLayout) dialogView.findViewById(R.id.llADQuantity);
        llADTotal = (LinearLayout) dialogView.findViewById(R.id.llADTotal);
        llNote = (LinearLayout) dialogView.findViewById(R.id.llNote);
        llCharge = (LinearLayout) dialogView.findViewById(R.id.llCharge);
        lblADTotal = (TextView) dialogView.findViewById(R.id.lblADTotal);
        if(newService){
            llADQuantity.setVisibility(View.GONE);
            llADTotal.setVisibility(View.GONE);
            llNote.setVisibility(View.VISIBLE);
            llCharge.setVisibility(View.VISIBLE);
        }else{
            llADQuantity.setVisibility(View.VISIBLE);
            llADTotal.setVisibility(View.VISIBLE);
            llNote.setVisibility(View.GONE);
            llCharge.setVisibility(View.GONE);
        }
        lblADAddress.setText(user.getAddress());
        lblADName.setText(user.getFirstName() + " " + user.getLastName());
        lblADPhoneNumber.setText(user.getphoneNumber() + "");
        lblADQuantity.setText(txtAdminBookCustomerQuantity.getText().toString());
//        Vinit K
        double amount = (Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString()));
        if(txtAdminBookDeliveryCharge.getText() != null && txtAdminBookDeliveryCharge.getText().toString().length()>0) {
            lblADTotal.setText( (amount + Integer.parseInt(txtAdminBookDeliveryCharge.getText().toString())) + "");
        }  else
            lblADTotal.setText(amount+"");
        lblADServiceCharge.setText(txtAdminBookServiceCharge.getText().toString() + "");
        lblADServiceNote.setText(txtAdminBookServiceNote.getText().toString() + "");
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Book", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setTitle("Saving Information");
                queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                String url = AppController.mainUrl+AppController.API_GAS_BOOKING;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response ", response);
                        if(response.toString().equals("200")) {
                            if(newService)
                                Snackbar.make(svQuickBook, "Service is Booked Successfully", Snackbar.LENGTH_LONG).show();
                            else {
                                Snackbar.make(svQuickBook, "Gas Bottle is Booked Successfully", Snackbar.LENGTH_LONG).show();
                                Calendar c = Calendar.getInstance();
                                String message = "Welcome to Datar Distributor Gas Agency. Dear consumer, Please Take Cash Ready Of Gas Cylinder " + lblADTotal.getText().toString() + "/-. Your Cylinder Booking date-" + c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + ". Distributor Will Deliver Your Cylinder Shortly. Check weight,seal,leakages before accepting delivery. Thank You.";
                                new SendSms(getActivity(), message, lblADPhoneNumber.getText().toString()).execute();
                                txtAdminBookCustomerNumber.setText("");
                                if(Domestic)
                                    spnAdminBookGasSize.setSelection(3);
                                else
                                    spnAdminBookGasSize.setSelection(1);
                                spnAdminBookDeliveryBoy.setSelection(0);
                                txtAdminBookCustomerQuantity.setText("1");
                                txtAdminBookDeliveryCharge.setText("0");
                                txtAdminBookServiceCharge.setText("");
                                txtAdminBookServiceNote.setText("");
                                txtAdminBookCustomerNumber.requestFocus();
                                rgAdminBookType.clearCheck();
                                rbAdminBookDomestic.setChecked(true);
                                chbService.setChecked(false);
                                spnAdminBookDeliveryBoy.setVisibility(View.GONE);
                                spnAdminBookGasSize.setVisibility(View.GONE);
                                inputAdminBookDeliveryCharge.setVisibility(View.GONE);
                                inputAdminBookCustomerQuantity.setVisibility(View.GONE);
                                lblBookGasSize.setVisibility(View.GONE);
                                llType.setVisibility(View.GONE);
                                lblBookTotal.setVisibility(View.GONE);
                                chbService.setVisibility(View.GONE);
                                btnAdminBook.setVisibility(View.GONE);
                            }
                            ChangeSizeCharge();
                        }
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response error",error.toString());
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put(AppController.GB_NAME, lblADName.getText().toString());
                        param.put(AppController.GB_DELIVERY_STATUS, false+"");
                        param.put(AppController.GB_DATE_OF_BOOKING,new Date()+"");
//                        param.put(AppController.GB_DATE_OF_DELIVERY,txtParticularCustomerLastName.getText().toString());
                        param.put(AppController.GB_USER_ID,DeliveryBoyId);
                        param.put(AppController.GB_IS_SERVICE,newService+"");
                        param.put(AppController.GB_USER_INFO_ID,SelectedUser+"");
                        if(newService){
                            param.put(AppController.GB_SERVICE_NOTE,txtAdminBookServiceNote.getText().toString());
                            param.put(AppController.GB_SERVICE_CHARGE,txtAdminBookServiceCharge.getText().toString());
                        }else{
                            param.put(AppController.GB_GAS_SIZE_ID, spnAdminBookGasSize.getSelectedItemPosition() + "");
                            if(txtAdminBookDeliveryCharge.getText()!=null || txtAdminBookDeliveryCharge.getText().toString().length()>0)
                                param.put(AppController.GB_DELIVERY_CHARGE,txtAdminBookDeliveryCharge.getText().toString());
                            else
                                param.put(AppController.GB_DELIVERY_CHARGE,"0");
                            param.put(AppController.GB_TOTAL,lblADTotal.getText().toString());
                            param.put(AppController.GB_QUANTITY,txtAdminBookCustomerQuantity.getText().toString());
                        }
                        return param;
                    }
                };
                queue.add(stringRequest);

            }
        });
        dialog.show();
    }

    private void ChangeSizeCharge() {
        queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        String url = AppController.mainUrl+AppController.API_MODIFY_SIZE_CHARGE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response ", response);
                if(response.toString().equals("200")) {
                    Snackbar.make(svQuickBook, "Data Successfully Saved", Snackbar.LENGTH_LONG).show();
                    ChangeSizeCharge();
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
                param.put(AppController.GB_USER_INFO_ID, lblADName.getText().toString());
                param.put(AppController.GB_GAS_SIZE_ID,spnAdminBookGasSize.getSelectedItemPosition()+"");
                param.put(AppController.GAS_TYPE_ID,0+"");
                if(txtAdminBookDeliveryCharge.getText()!=null || txtAdminBookDeliveryCharge.getText().toString().length()>0) {
                    param.put(AppController.GB_DELIVERY_CHARGE, txtAdminBookDeliveryCharge.getText().toString());
                }else
                    param.put(AppController.GB_DELIVERY_CHARGE, 0+"");
                param.put(AppController.GB_USER_ID,DeliveryBoyId);
                return param;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnAdminBookGasSize:
                Price = gasSizeList.get(position).getPrice();
                if (txtAdminBookCustomerQuantity.getText() == null || txtAdminBookCustomerQuantity.getText().length() <= 0){
                    if(txtAdminBookDeliveryCharge.getText()!=null && txtAdminBookDeliveryCharge.getText().toString().length()>0)
                        lblAdminBookTotal.setText((Price + Integer.parseInt(txtAdminBookDeliveryCharge.getText().toString()) + ""));
                    else
                        lblAdminBookTotal.setText((Price+""));
                }else {
                    if(txtAdminBookDeliveryCharge.getText()!=null && txtAdminBookDeliveryCharge.getText().toString().length()>0) {
                        double amount = ((Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString())) + Integer.parseInt(txtAdminBookDeliveryCharge.getText().toString()));
                        lblAdminBookTotal.setText(amount + "");
                    }else{
                        double amount = ((Price * Integer.parseInt(txtAdminBookCustomerQuantity.getText().toString())));
                        lblAdminBookTotal.setText(amount + "");
                    }
                }
                break;
            case R.id.spnAdminBookDeliveryBoy:
                DeliveryBoyId = DeliveryBoyList.get(position).getObjectId();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()){
            case R.id.rbAdminBookCommercial :
                gasTypePosition = 1;
                Domestic = false;
                if(!fromWhere)
                    SettingGasSize(2,0);
                break;
            case R.id.rbAdminBookDomestic :
                gasTypePosition = 0;
                Domestic = true;
                if(!fromWhere)
                    SettingGasSize(1,0);
                break;
        }
        fromWhere = false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        newService = isChecked;
        if(isChecked){
            inputAdminBookCustomerQuantity.setVisibility(View.GONE);
            inputAdminBookDeliveryCharge.setVisibility(View.GONE);
            llType.setVisibility(View.GONE);
            lblBookGasSize.setVisibility(View.GONE);
            spnAdminBookGasSize.setVisibility(View.GONE);
            lblBookTotal.setVisibility(View.GONE);
            inputAdminBookServiceCharge.setVisibility(View.VISIBLE);
            inputAdminBookServiceNote.setVisibility(View.VISIBLE);
        }else{
            inputAdminBookCustomerQuantity.setVisibility(View.VISIBLE);
            inputAdminBookDeliveryCharge.setVisibility(View.VISIBLE);
            llType.setVisibility(View.VISIBLE);
            lblBookGasSize.setVisibility(View.VISIBLE);
            spnAdminBookGasSize.setVisibility(View.VISIBLE);
            lblBookTotal.setVisibility(View.VISIBLE);
            inputAdminBookServiceCharge.setVisibility(View.GONE);
            inputAdminBookServiceNote.setVisibility(View.GONE);
        }
    }
}