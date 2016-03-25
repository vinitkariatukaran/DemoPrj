package com.demoprj.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demoprj.Adapter.CustomerDetailAdapter;
import com.demoprj.Adapter.ExpenseDetailAdapter;
import com.demoprj.AdminHomeActivity;
import com.demoprj.BaseRequest.RestModel.ExpenseDetail;
import com.demoprj.BaseRequest.RestModel.GasSizePrice;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.Expense;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.User;
import com.demoprj.R;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDetailsFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    RecyclerView rvExpenseDetail;
    RecyclerView.Adapter adapter;
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
    //    Date selectDate = null;
    String selectedDate;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
        currentDate = day + "-" + (month + 1) + "-" + year;
        btnExpenseAdminDate.setText(day + "-" + (month + 1) + "-" + year);
        String month_temp = String.valueOf(month+1);
        String day_temp = String.valueOf(day);
        if ((month + 1) < 10) {

            month_temp = 0 + String.valueOf(month + 1);
        }

        if (day < 10) {

            day_temp = 0 + String.valueOf(day);
        }
        selectedDate = year + "-" + (month_temp) + "-" + day_temp;
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
        if (Utils.isInternetAvailable(getActivity())) {
            pDialog.show();
//            try {
//                selectDate = format.parse(btnExpenseAdminDate.getText().toString());
//
//            } catch (java.text.ParseException e1) {
//                e1.printStackTrace();
//            }
//            ParseQuery<Expense> userListQuery = ParseQuery.getQuery(Expense.class);
//            userListQuery.whereEqualTo(AppConstant.EXPENSE_DATE, selectDate);
//            userListQuery.include(AppConstant.EXPENSE_DELIVERYBOY);
//            userListQuery.findInBackground(new FindCallback<Expense>() {
//                @Override
//                public void done(List<Expense> objects, ParseException e) {
//                    if (e == null) {
//                        if (objects.size() > 0) {
//                            adapter = new ExpenseDetailAdapter(getActivity(), (ArrayList<Expense>) objects, btnExpenseAdminDate.getText().toString());
//                            rvExpenseDetail.setAdapter(adapter);
//                        } else {
//                            adapter = new ExpenseDetailAdapter(getActivity(), new ArrayList<Expense>(), btnExpenseAdminDate.getText().toString());
//                            rvExpenseDetail.setAdapter(adapter);
//                        }
//                    } else {
//                    }
//                    pDialog.dismiss();
//                }
//            });
            RequestQueue queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
            String url = AppController.mainUrl + AppController.API_AMOUNT_DETAIL;
            Type type = new TypeToken<List<ExpenseDetail>>() {
            }.getType();
            baseModel bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Log.e("response", response.toString());
                    adapter = new ExpenseDetailAdapter(getActivity(), (ArrayList<ExpenseDetail>) response, selectedDate);
                    rvExpenseDetail.setAdapter(adapter);
                    pDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response error", error.toString());
                    pDialog.dismiss();
//                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put(AppController.DATE, String.valueOf(selectedDate));
                    return param;
                }
            };
            queue.add(bm);
        } else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        dpDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dpDialog.getDatePicker().setSpinnersShown(true);
        dpDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        btnExpenseAdminDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        String month_temp = String.valueOf(month + 1);
        String day_temp = String.valueOf(day);

        DecimalFormat mFormat= new DecimalFormat("00");
        mFormat.format(Double.valueOf(year));

        if ((month + 1) < 10) {

            month_temp = "0" + String.valueOf(month + 1);
        }

        if (day < 10) {

            day_temp = "0" + String.valueOf(day);
        }
        selectedDate = year + "-" + (month_temp) + "-" + day_temp;
        FindDetail();
    }
}