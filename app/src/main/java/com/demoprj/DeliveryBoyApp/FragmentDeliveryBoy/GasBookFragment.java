package com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy;

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

import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GasBookFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextInputLayout inputDeliveryBoyBookCustomerNumber;
    TextInputLayout inputDeliveryBoyBookServiceNote;
    TextInputLayout inputDeliveryBoyBookServiceCharge;

    EditText txtDeliveryBoyBookCustomerNumber;
    EditText txtDeliveryBoyBookServiceNote;
    EditText txtDeliveryBoyBookServiceCharge;

    CheckBox chbDeliveryBoyService;

    TextView lblDeliveryBoyBookTotal;
    Button btnDeliveryBoyBook;
    Button btnDeliveryBoyBookDone;
    NestedScrollView svDeliveryBoyQuickBook;
    boolean fromWhere = false;

    ArrayList<GasSize> gasSizeList = new ArrayList<GasSize>();

    TextView lblADName;
    TextView lblADAddress;
    TextView lblADServiceNote;
    TextView lblADServiceCharge;
    TextView lblADPhoneNumber;
    TextView lblADQuantity;
    TextView lblADTotal;
    LinearLayout llBookTotal;
    LinearLayout llADQuantity;
    LinearLayout llADTotal;
    LinearLayout llNote;
    LinearLayout llCharge;
    double Price=1;
    User SelectedUser;
    ProgressDialog progressDialog;
    TextView lblDeliveryBoyBookGasSize;
    boolean newService = false;
    boolean Domestic = true;
    ViewGroup mainContainer;
    InputMethodManager imm;
    int gasTypePosition;
    String gasSizeObjectId = "";
    int DeliveryCharge = 0;
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
        View rootView = inflater.inflate(R.layout.fragment_qas_book_deliveryboy, container, false);
        mainContainer = container;
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait");
        llBookTotal = (LinearLayout) rootView.findViewById(R.id.llBookTotal);
        inputDeliveryBoyBookCustomerNumber = (TextInputLayout) rootView.findViewById(R.id.inputDeliveryBoyBookCustomerNumber);
        lblDeliveryBoyBookGasSize = (TextView) rootView.findViewById(R.id.lblDeliveryBoyBookGasSize);
        inputDeliveryBoyBookServiceCharge = (TextInputLayout) rootView.findViewById(R.id.inputDeliveryBoyBookServiceCharge);
        inputDeliveryBoyBookServiceNote = (TextInputLayout) rootView.findViewById(R.id.inputDeliveryBoyBookServiceNote);

        txtDeliveryBoyBookCustomerNumber = (EditText) rootView.findViewById(R.id.txtDeliveryBoyBookCustomerNumber);
        txtDeliveryBoyBookServiceNote = (EditText) rootView.findViewById(R.id.txtDeliveryBoyBookServiceNote);
        txtDeliveryBoyBookServiceCharge = (EditText) rootView.findViewById(R.id.txtDeliveryBoyBookServiceCharge);

        chbDeliveryBoyService = (CheckBox) rootView.findViewById(R.id.chbDeliveryBoyService);

        lblDeliveryBoyBookTotal = (TextView) rootView.findViewById(R.id.lblDeliveryBoyBookTotal);
        btnDeliveryBoyBook = (Button) rootView.findViewById(R.id.btnDeliveryBoyBook);
        btnDeliveryBoyBookDone = (Button) rootView.findViewById(R.id.btnDeliveryBoyBookDone);

        svDeliveryBoyQuickBook = (NestedScrollView) rootView.findViewById(R.id.svDeliveryBoyQuickBook);
        lblDeliveryBoyBookGasSize.setVisibility(View.GONE);
        llBookTotal.setVisibility(View.GONE);
        chbDeliveryBoyService.setVisibility(View.GONE);
        btnDeliveryBoyBook.setVisibility(View.GONE);
        btnDeliveryBoyBook.setOnClickListener(this);
        btnDeliveryBoyBookDone.setOnClickListener(this);
        chbDeliveryBoyService.setOnCheckedChangeListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeliveryBoyBookDone:
                if(txtDeliveryBoyBookCustomerNumber.getText()!=null && txtDeliveryBoyBookCustomerNumber.getText().toString().length()>0) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (Utils.isInternetAvailable(getActivity())) {
                        txtDeliveryBoyBookServiceCharge.setText("");
                        txtDeliveryBoyBookServiceNote.setText("");
                        final ProgressDialog pDialog = new ProgressDialog(getActivity());
                        pDialog.setTitle("Fetching User Info");
                        pDialog.setMessage("Please Wait");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
                        userQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, Integer.parseInt(txtDeliveryBoyBookCustomerNumber.getText().toString()));
                        userQuery.findInBackground(new FindCallback<User>() {
                            @Override
                            public void done(List<User> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        final User selectedUser = objects.get(0);
                                        if(objects.get(0).has(AppConstant.USER_DELIVERY_CHARGE)){
                                            if(objects.get(0).getDeliveryCharge()>0){
                                                chbDeliveryBoyService.setEnabled(true);
                                                GasType gt = (GasType) selectedUser.get(AppConstant.USER_GAS_TYPE_ID);
                                                ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
                                                gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gt.getObjectId());
                                                gasTypeQuery.findInBackground(new FindCallback<GasType>() {
                                                    @Override
                                                    public void done(List<GasType> objects, ParseException e) {
                                                        if (e == null) {
                                                            int pos = 0;
                                                            if (objects.size() > 0) {
                                                                if (objects.get(0).getGasType().toLowerCase().equals("commercial")) {
                                                                    pos = 2;
                                                                } else if (objects.get(0).getGasType().toLowerCase().equals("domestic")) {
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
                                                                                lblDeliveryBoyBookGasSize.setText("Gas Size : " + gasSizeList.get(selectedUser.getGasSize()).getGasSize());
                                                                                gasSizeObjectId = gasSizeList.get(selectedUser.getGasSize()).getObjectId();
                                                                                lblDeliveryBoyBookTotal.setText((selectedUser.getDeliveryCharge()+gasSizeList.get(selectedUser.getGasSize()).getPrice()) + "");
                                                                                DeliveryCharge = selectedUser.getDeliveryCharge();
                                                                            }
                                                                        }
                                                                        newService = false;
                                                                        inputDeliveryBoyBookServiceCharge.setVisibility(View.GONE);
                                                                        inputDeliveryBoyBookServiceNote.setVisibility(View.GONE);
                                                                        llBookTotal.setVisibility(View.VISIBLE);
                                                                        lblDeliveryBoyBookGasSize.setVisibility(View.VISIBLE);
                                                                        lblDeliveryBoyBookTotal.setVisibility(View.VISIBLE);
                                                                        chbDeliveryBoyService.setVisibility(View.VISIBLE);
                                                                        btnDeliveryBoyBook.setVisibility(View.VISIBLE);
                                                                    }
                                                                });
                                                            }
                                                        } else {

                                                        }
                                                    }
                                                });

                                            }else{
                                                newService = true;
                                                DisplayOnlyService();
                                            }
                                        }else{
                                            newService = true;
                                            DisplayOnlyService();
                                        }

                                    } else {
                                        Snackbar.make(svDeliveryBoyQuickBook, "Please enter correct Cutomer Number", Snackbar.LENGTH_LONG).show();
                                        llBookTotal.setVisibility(View.GONE);
                                        lblDeliveryBoyBookGasSize.setVisibility(View.GONE);
                                        lblDeliveryBoyBookTotal.setVisibility(View.GONE);
                                        chbDeliveryBoyService.setVisibility(View.GONE);
                                        btnDeliveryBoyBook.setVisibility(View.GONE);
                                        inputDeliveryBoyBookServiceNote.setVisibility(View.GONE);
                                        inputDeliveryBoyBookServiceCharge.setVisibility(View.GONE);
                                    }
                                }
                                pDialog.dismiss();
                            }
                        });
                    }
                }else{
                    Snackbar.make(svDeliveryBoyQuickBook, "Please enter Cutomer Number", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDeliveryBoyBook:
                btnDeliveryBoyBook.setEnabled(false);
                if(txtDeliveryBoyBookCustomerNumber.getText()==null || txtDeliveryBoyBookCustomerNumber.getText().length()<=0){
                    Snackbar.make(svDeliveryBoyQuickBook, "Please enter Cutomer Number", Snackbar.LENGTH_LONG).show();
                    txtDeliveryBoyBookCustomerNumber.requestFocus();
                    inputDeliveryBoyBookCustomerNumber.setError(getResources().getString(R.string.Field_require));
                    inputDeliveryBoyBookCustomerNumber.setErrorEnabled(true);
                    btnDeliveryBoyBook.setEnabled(true);
                }else if(newService){
                    if(txtDeliveryBoyBookServiceCharge.getText()==null || txtDeliveryBoyBookServiceCharge.getText().length()<=0){
                        Snackbar.make(svDeliveryBoyQuickBook, "Please enter Service Charge", Snackbar.LENGTH_LONG).show();
                        txtDeliveryBoyBookServiceCharge.requestFocus();
                        inputDeliveryBoyBookServiceCharge.setError(getResources().getString(R.string.Field_require));
                        inputDeliveryBoyBookServiceCharge.setErrorEnabled(true);
                        inputDeliveryBoyBookServiceNote.setErrorEnabled(false);
                        btnDeliveryBoyBook.setEnabled(true);
                    }else if(txtDeliveryBoyBookServiceNote.getText()==null || txtDeliveryBoyBookServiceNote.getText().length()<=0){
                        Snackbar.make(svDeliveryBoyQuickBook, "Please enter Service Note", Snackbar.LENGTH_LONG).show();
                        txtDeliveryBoyBookServiceNote.requestFocus();
                        inputDeliveryBoyBookServiceNote.setError(getResources().getString(R.string.Field_require));
                        inputDeliveryBoyBookServiceNote.setErrorEnabled(true);
                        inputDeliveryBoyBookServiceCharge.setErrorEnabled(false);
                        btnDeliveryBoyBook.setEnabled(true);
                    }else{
                        DisplayAlertDialog();
                    }
                }else{
                    DisplayAlertDialog();
                }
                break;
        }
    }

    private void DisplayOnlyService() {
        chbDeliveryBoyService.setEnabled(false);
        chbDeliveryBoyService.setVisibility(View.VISIBLE);
        Snackbar.make(svDeliveryBoyQuickBook, "Sorry You can only book new service not any gas bottle.", Snackbar.LENGTH_LONG).show();
        llBookTotal.setVisibility(View.GONE);
        lblDeliveryBoyBookGasSize.setVisibility(View.GONE);
        lblDeliveryBoyBookTotal.setVisibility(View.GONE);
        inputDeliveryBoyBookServiceCharge.setVisibility(View.VISIBLE);
        inputDeliveryBoyBookServiceNote.setVisibility(View.VISIBLE);
        btnDeliveryBoyBook.setVisibility(View.VISIBLE);
    }

    private void DisplayAlertDialog() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        progressDialog.setTitle("Fetching UserInfo");
        if(Utils.isInternetAvailable(getActivity())) {
            progressDialog.show();
            ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
            userQuery.whereEqualTo(AppConstant.USER_CUSTOMER_ID, Integer.parseInt(txtDeliveryBoyBookCustomerNumber.getText().toString()));
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
                            dialog.setMessage("Sorry! No User found on " + txtDeliveryBoyBookCustomerNumber.getText().toString() + " Customer Number");
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
                        btnDeliveryBoyBook.setEnabled(true);
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
        lblADQuantity.setText("1");
        lblADTotal.setText(lblDeliveryBoyBookTotal.getText().toString());
        lblADServiceCharge.setText(txtDeliveryBoyBookServiceCharge.getText().toString() + "");
        lblADServiceNote.setText(txtDeliveryBoyBookServiceNote.getText().toString() + "");
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
                GasBooking gas = new GasBooking();
                gas.setName(lblADName.getText().toString());
                gas.setUserId(SelectedUser.getObjectId());
                gas.setDeliverySatus(false);
                if(newService){
                    gas.setServiceCharge(txtDeliveryBoyBookServiceCharge.getText().toString());
                    gas.setServiceNote(txtDeliveryBoyBookServiceNote.getText().toString());
                }else{
                    gas.setTotal(Double.parseDouble(lblADTotal.getText().toString()));
                    gas.setQuantity(1);
                    gas.setGasSizeId(gasSizeObjectId );
                    gas.setDeliveryCharge(DeliveryCharge+"");
                }
                gas.setisService(newService);
                gas.setDeliveryBoyId(ParseUser.getCurrentUser().getObjectId());
                gas.setDateOfBooking(new Date());
                gas.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            if(newService)
                                Snackbar.make(svDeliveryBoyQuickBook, "Service is Booked Successfully", Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(svDeliveryBoyQuickBook, "Gas Bottle is Booked Successfully", Snackbar.LENGTH_LONG).show();
                            lblDeliveryBoyBookGasSize.setVisibility(View.GONE);
                            llBookTotal.setVisibility(View.GONE);
                            chbDeliveryBoyService.setVisibility(View.GONE);
                            btnDeliveryBoyBook.setVisibility(View.GONE);
                            inputDeliveryBoyBookServiceCharge.setVisibility(View.GONE);
                            inputDeliveryBoyBookServiceNote.setVisibility(View.GONE);
                            txtDeliveryBoyBookCustomerNumber.setText("");
                            txtDeliveryBoyBookServiceCharge.setText("");
                            txtDeliveryBoyBookServiceNote.setText("");
                        } else {
                            Snackbar.make(svDeliveryBoyQuickBook, "Some problem Occured. Please try again after sometime", Snackbar.LENGTH_LONG).show();
                        }
                        if(progressDialog!=null)
                            progressDialog.hide();
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        newService = isChecked;
        if(isChecked){
            llBookTotal.setVisibility(View.GONE);
            lblDeliveryBoyBookGasSize.setVisibility(View.GONE);
            lblDeliveryBoyBookTotal.setVisibility(View.GONE);
            inputDeliveryBoyBookServiceCharge.setVisibility(View.VISIBLE);
            inputDeliveryBoyBookServiceNote.setVisibility(View.VISIBLE);
        }else{
            llBookTotal.setVisibility(View.VISIBLE);
            lblDeliveryBoyBookGasSize.setVisibility(View.VISIBLE);
            lblDeliveryBoyBookTotal.setVisibility(View.VISIBLE);
            inputDeliveryBoyBookServiceCharge.setVisibility(View.GONE);
            inputDeliveryBoyBookServiceNote.setVisibility(View.GONE);
        }
    }
}