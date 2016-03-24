package com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.demoprj.AdminHomeActivity;
import com.demoprj.Constant.AppConstant;
import com.demoprj.DeliveryboyHomeActivity;
import com.demoprj.ParseModel.Expense;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenseFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText txtExpenseDetail;
    EditText txtExpenseCharge;
    TextInputLayout inputExpenseCharge;
    TextInputLayout inputExpenseDetail;

    Button btnExpenseSubmit;
    Button btnExpenseDate;
    ScrollView svExpense;
    int year;
    int month;
    int day;
    Calendar calender;
    DatePickerDialog dpDialog;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    Date selectDate;
    String currentDate;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.fragment_expense_deliveryboy, container, false);
        inputExpenseCharge = (TextInputLayout) rootView.findViewById(R.id.inputExpenseCharge);
        inputExpenseDetail = (TextInputLayout) rootView.findViewById(R.id.inputExpenseDetail);
        txtExpenseCharge = (EditText) rootView.findViewById(R.id.txtExpenseCharge);
        txtExpenseDetail = (EditText) rootView.findViewById(R.id.txtExpenseDetail);
        svExpense = (ScrollView) rootView.findViewById(R.id.svExpense);

        btnExpenseSubmit = (Button) rootView.findViewById(R.id.btnExpenseSubmit);
        btnExpenseDate = (Button) rootView.findViewById(R.id.btnExpenseDate);

        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
        currentDate = day+"-"+(month+1)+"-"+year;
        btnExpenseDate.setText(day+"-"+(month+1)+"-"+year);
        btnExpenseSubmit.setOnClickListener(this);
        btnExpenseDate.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExpenseSubmit:
                int hr = calender.get(Calendar.HOUR_OF_DAY);
                int min = calender.get(Calendar.MINUTE);
                String timeString = hr+":"+min;
                try {
                    Date time = formatter.parse(timeString);
                    Date time1 = formatter.parse("22:30");
                    if(time.compareTo(time1)<0){
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        if(txtExpenseDetail.getText() == null || txtExpenseDetail.getText().toString().length()<=0){
                            Snackbar.make(svExpense,"Please enter Expense detail",Snackbar.LENGTH_LONG).show();
                            txtExpenseDetail.requestFocus();
                            inputExpenseDetail.setError(getResources().getString(R.string.Field_require));
                            inputExpenseDetail.setErrorEnabled(true);
                        }else if(txtExpenseCharge.getText() == null || txtExpenseCharge.getText().toString().length()<=0){
                            inputExpenseDetail.setErrorEnabled(false);
                            txtExpenseCharge.requestFocus();
                            Snackbar.make(svExpense,"Please enter Expense Charge",Snackbar.LENGTH_LONG).show();
                            inputExpenseCharge.setError(getResources().getString(R.string.Field_require));
                        }else {
                            inputExpenseDetail.setErrorEnabled(false);
                            inputExpenseCharge.setErrorEnabled(false);
                            if (Utils.isInternetAvailable(getActivity())) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity());
                                dialog.setTitle("Saving Detail");
                                dialog.setMessage("Please wait");
                                dialog.show();
                                try {
                                    selectDate = format.parse(btnExpenseDate.getText().toString());
                                    ParseQuery<Expense> expenseQuery = ParseQuery.getQuery(Expense.class);
                                    expenseQuery.whereEqualTo(AppConstant.EXPENSE_DATE,selectDate);
                                    expenseQuery.whereEqualTo(AppConstant.EXPENSE_DELIVERYBOY,ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
                                    expenseQuery.findInBackground(new FindCallback<Expense>() {
                                        @Override
                                        public void done(List<Expense> objects, ParseException e) {
                                            if(e==null){
                                                if(objects.size()>0){
                                                    objects.get(0).setDeliveryBoyId(ParseUser.getCurrentUser().getObjectId());
                                                    objects.get(0).setAmount(Long.parseLong(txtExpenseCharge.getText().toString()));
                                                    objects.get(0).setDetail(txtExpenseDetail.getText().toString());
                                                    objects.get(0).setDate(selectDate);
                                                    objects.get(0).saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if(e==null){
                                                                Snackbar.make(svExpense,"Your detail saved successfully",Snackbar.LENGTH_LONG).show();
                                                                clearDate();
                                                            }else{

                                                            }
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                }else{
                                                    Expense expense = new Expense();
                                                    expense.setDetail(txtExpenseDetail.getText().toString());
                                                    expense.setDate(selectDate);
                                                    expense.setAmount(Long.parseLong(txtExpenseCharge.getText().toString()));
                                                    expense.setDeliveryBoyId(ParseUser.getCurrentUser().getObjectId());
                                                    expense.setDeliveryAmount(0.0);
                                                    expense.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if(e==null){
                                                                Snackbar.make(svExpense,"Your detail saved successfully",Snackbar.LENGTH_LONG).show();
                                                                clearDate();
                                                            }else{

                                                            }
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                }
                                            }else{
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Snackbar.make(svExpense,"Time is over for saving expense detail",Snackbar.LENGTH_LONG).show();
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnExpenseDate:
                dpDialog = new DatePickerDialog(getActivity(),this,year,month,day);
                dpDialog.getDatePicker().setSpinnersShown(true);
                dpDialog.show();
                break;
        }

    }

    private void clearDate() {
        txtExpenseDetail.setText("");
        txtExpenseCharge.setText("");
        btnExpenseDate.setText(currentDate);
        txtExpenseDetail.requestFocus();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        btnExpenseDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
    }
}
