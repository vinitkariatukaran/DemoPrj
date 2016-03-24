package com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.demoprj.Adapter.ExpenseDetailAdapter;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.Expense;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AmountExpenseDetailsFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    RecyclerView rvExpenseDetail;
    RecyclerView.Adapter adapter ;
    RecyclerView.LayoutManager mLayoutManager;

    Button btnExpenseAdminDate;
    int year;
    int month;
    int day;
    Calendar calender;
    String currentDate;
    DatePickerDialog dpDialog;
    String[] DeliveryboyId;
    Double[] DeliveryBoyTotlaAmount;
    long[] DeliveryBoyExpense;
    long[] DeliveryBoyPaidAmount;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        rvExpenseDetail = (RecyclerView) rootView.findViewById(R.id.rvExpenseDetail);
        btnExpenseAdminDate = (Button) rootView.findViewById(R.id.btnExpenseAdminDate);
        rvExpenseDetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvExpenseDetail.setLayoutManager(mLayoutManager);
        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
        currentDate = day+"-"+(month+1)+"-"+year;
        btnExpenseAdminDate.setText(day + "-" + (month + 1) + "-" + year);
        btnExpenseAdminDate.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FindDetail();

    }

    private void FindDetail() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setTitle("Fetching Detail");
        pDialog.setMessage("Please wait");
        if(Utils.isInternetAvailable(getActivity())) {
            pDialog.show();
            Date selectDate = null;
            try {
                selectDate = format.parse(btnExpenseAdminDate.getText().toString());
            } catch (java.text.ParseException e1) {
                e1.printStackTrace();
            }
            ParseQuery<Expense> userListQuery = ParseQuery.getQuery(Expense.class);
            userListQuery.whereEqualTo(AppConstant.EXPENSE_DATE, selectDate);
            userListQuery.whereEqualTo(AppConstant.EXPENSE_DELIVERYBOY, ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
            userListQuery.include(AppConstant.EXPENSE_DELIVERYBOY);
            userListQuery.findInBackground(new FindCallback<Expense>() {
                @Override
                public void done(List<Expense> objects, ParseException e) {
                    if (e == null) {
                        if(objects.size()>0){
                            adapter = new ExpenseDetailAdapter(getActivity(), (ArrayList<Expense>) objects,btnExpenseAdminDate.getText().toString());
                            rvExpenseDetail.setAdapter(adapter);
                        }else{
                            adapter = new ExpenseDetailAdapter(getActivity(), new ArrayList<Expense>(),btnExpenseAdminDate.getText().toString());
                            rvExpenseDetail.setAdapter(adapter);
                        }
                    }
                    else
                    {
                    }
                    pDialog.dismiss();
                }
            });
        }else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        dpDialog = new DatePickerDialog(getActivity(),this,year,month,day);
        dpDialog.getDatePicker().setSpinnersShown(true);
        dpDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        btnExpenseAdminDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        FindDetail();
    }
}