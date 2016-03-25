package com.demoprj;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demoprj.Adapter.DeliveryBoyExpenseDetailAdapter;
import com.demoprj.Adapter.ExpenseDetailAdapter;
import com.demoprj.BaseRequest.RestModel.ExpenseDetail;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.Expense;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.ExpenseDetailModel;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryBoyWiseDetailActivity extends AppCompatActivity {

    ArrayList<ExpenseDetailModel> expenseModel = new ArrayList<>();
    ArrayList<ExpenseDetailModel> finalExpenseModel = new ArrayList<>();
    String date = "17/2/2016";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String objectId = "";
//    String[] seperatedDate;
    String selectedDate;
    String day,month;
    ProgressDialog pDialog;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView rvDeliveryBoyExpenseDetail;
    LinearLayout detailExpenseLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_wise_detail);
        rvDeliveryBoyExpenseDetail = (RecyclerView) findViewById(R.id.rvDeliveryBoyExpenseDetail);
        mLayoutManager = new LinearLayoutManager(this);
        rvDeliveryBoyExpenseDetail.setLayoutManager(mLayoutManager);
        detailExpenseLayout = (LinearLayout) findViewById(R.id.detailExpenseLayout);
        Bundle b = getIntent().getExtras();
        date = b.getString("Date");
        selectedDate = b.getString("Date");
        objectId = b.getString("ObjectId");
//        seperatedDate = date.split("-");
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Fetching Detail");
        pDialog.setMessage("Please Wait");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        RequestQueue queue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = AppController.mainUrl + AppController.API_DELIEVERY_BOTTLE_DETAIL;
        Type type = new TypeToken<List<ExpenseDetail>>() {
        }.getType();
        baseModel bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.e("response", response.toString());
//                adapter = new ExpenseDetailAdapter( (ArrayList<ExpenseDetail>) response, selectedDate);
//                rvExpenseDetail.setAdapter(adapter);
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
                param.put(AppController.GB_USER_ID, objectId);
                return param;
            }
        };
        queue.add(bm);
        //        if(Integer.parseInt(seperatedDate[0].toString())<10){
//            day = "0"+seperatedDate[0];
//        }else{
//            day = seperatedDate[0];
//        }
//        if(Integer.parseInt(seperatedDate[1].toString())<10){
//            month = "0"+seperatedDate[1];
//        }else{
//            month = seperatedDate[1];
//        }
//        date = day+"-"+month+"-"+seperatedDate[2];
//        ParseQuery<GasSize> gasQuery = ParseQuery.getQuery(GasSize.class);
//        gasQuery.findInBackground(new FindCallback<GasSize>() {
//            @Override
//            public void done(List<GasSize> objects, ParseException e) {
//                if(e==null){
//                    if(objects.size()>0){
//                     for(int i = 0;i<objects.size();i++){
//                         ExpenseDetailModel edModel = new ExpenseDetailModel(objects.get(i).getObjectId(),objects.get(i).getGasSize(),0,0.0,false);
//                         expenseModel.add(edModel);
//                     }
//                        ExpenseDetailModel edModel = new ExpenseDetailModel("Service",0.0,0,0.0,true);
//                        expenseModel.add(edModel);
//                    }
//                    try {
//                        Date d = sdf.parse(selectedDate);
//                        Calendar c = Calendar.getInstance();
//                        c.setTime(d);
//                        c.add(Calendar.DATE,1);
//                        Date next = c.getTime();
//                        ParseQuery<GasBooking> gasBookingParseQuery = ParseQuery.getQuery(GasBooking.class);
//                        gasBookingParseQuery.whereGreaterThanOrEqualTo(AppConstant.GASBOOKING_DATE_OF_DELIVERY,d);
//                        gasBookingParseQuery.whereLessThanOrEqualTo(AppConstant.GASBOOKING_DATE_OF_DELIVERY, next);
//                        gasBookingParseQuery.whereEqualTo(AppConstant.GASBOOKING_DELIVERYBOY_ID, ParseObject.createWithoutData("_User", objectId));
//                        gasBookingParseQuery.include(AppConstant.GASBOOKING_GAS_SIZE_ID);
//                        gasBookingParseQuery.findInBackground(new FindCallback<GasBooking>() {
//                            @Override
//                            public void done(List<GasBooking> objects, ParseException e) {
//                                if(e == null){
//                                    if(objects.size()>0){
//                                        for(int i = 0 ; i<objects.size();i++){
//                                            for(int j = 0 ; j < expenseModel.size();j++){
//                                                if(objects.get(i).getisService()){
//                                                    if (expenseModel.get(j).getGasSizeId().equals("Service")){
//                                                        Double total = expenseModel.get(j).getAmount()+ Double.parseDouble(objects.get(i).getServiceCharge());
//                                                        expenseModel.get(j).setTotalQty(expenseModel.get(j).getTotalQty()+1);
//                                                        expenseModel.get(j).setAmount(total);
//                                                        expenseModel.get(j).setService(true);
//                                                        break;
//                                                    }
//                                                }else {
//                                                    GasSize gs = (GasSize) objects.get(i).get(AppConstant.GASBOOKING_GAS_SIZE_ID);
//                                                    if (expenseModel.get(j).getGasSizeId().equals(gs.getObjectId())) {
//                                                        expenseModel.get(j).setService(false);
//                                                        int Qty= expenseModel.get(j).getTotalQty()+objects.get(i).getQuantity();
//                                                        Double Amount = expenseModel.get(j).getAmount()+objects.get(i).getTotal();
//                                                        expenseModel.get(j).setTotalQty(Qty);
//                                                        expenseModel.get(j).setAmount(Amount);
//                                                        break;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        for(int z = 0;z<expenseModel.size();z++){
//                                            if(expenseModel.get(z).getTotalQty()>0){
//                                                finalExpenseModel.add(expenseModel.get(z));
//                                            }
//                                        }
//                                        if(finalExpenseModel.size()>0){
//                                            adapter = new DeliveryBoyExpenseDetailAdapter(DeliveryBoyWiseDetailActivity.this, finalExpenseModel);
//                                            rvDeliveryBoyExpenseDetail.setAdapter(adapter);
//                                        }else{
//                                            Snackbar.make(detailExpenseLayout,"There is no data to Display",Snackbar.LENGTH_LONG).show();
//                                        }
//
//                                    }
//                                    pDialog.dismiss();
//                                }else{
//                                    pDialog.dismiss();
//                                }
//                            }
//                        });
//                    } catch (java.text.ParseException e1) {
//                        e1.printStackTrace();
//                    }
//                }else{
//                    pDialog.dismiss();
//                }
//            }
//        });
    }
}
