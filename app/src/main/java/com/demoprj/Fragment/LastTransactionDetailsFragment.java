package com.demoprj.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demoprj.Adapter.TransactionDetailAdapter;
import com.demoprj.AdminHomeActivity;
import com.demoprj.BaseRequest.RestModel.Login;
import com.demoprj.BaseRequest.RestModel.TransactionDetail;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.DeliveryboyHomeActivity;
import com.demoprj.GsonModel.PACK.GasSizeDetailModel;
import com.demoprj.ParseModel.DeliveryModel;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.demoprj.View.SegmentedGroup;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.GetCallback;
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

public class LastTransactionDetailsFragment extends Fragment {
    public static RecyclerView rvTransactionDetail;
    public static RecyclerView.Adapter adapter ;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    public static LinearLayout llDeliverySearch;
    public static EditText txtDeliverySearch;
    ImageButton ibDeliverySearchClose;
    Button btnDeliverySearch;
    int searchPosition = 0;
    ArrayList<GasBooking> gasBookingList = new ArrayList<GasBooking>();
    Calendar now2 = null;


    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.transaction_detail, container, false);
        rvTransactionDetail = (RecyclerView) rootView.findViewById(R.id.rvTransactionDetail);
        rvTransactionDetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvTransactionDetail.setLayoutManager(mLayoutManager);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Fetching Detail");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (Utils.isInternetAvailable(getActivity())) {
            progressDialog.show();
//            ParseQuery<GasBooking> GasQuery = ParseQuery.getQuery(GasBooking.class);
//            GasQuery.whereEqualTo(AppConstant.GASBOOKING_USER_ID, ParseObject.createWithoutData("UserInfo", ParticularCustomerDetailInfo.ObjectId));
//            GasQuery.orderByDescending(AppConstant.GASBOOKING_DATE_OF_BOOKING);
//            GasQuery.findInBackground(new FindCallback<GasBooking>() {
//                @Override
//                public void done(List<GasBooking> objects, ParseException e) {
//                    if(e==null){
//                        if(objects.size()>0){
//                            adapter = new TransactionDetailAdapter(getActivity(), (ArrayList<GasBooking>) objects);
//                            rvTransactionDetail.setAdapter(adapter);
//                        }
//                    }else{
//
//                    }
//                    progressDialog.dismiss();
//                }
//            });

            RequestQueue queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
            String url = AppController.mainUrl + AppController.API_GETCUSTOMERLASTTRANS;
            Type type =  new TypeToken<List<TransactionDetail>>(){}.getType();
            baseModel<Login> bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    List<TransactionDetail> transactionDetailList = (List<TransactionDetail>) response;
//                    adapter = new TransactionDetailAdapter(getActivity(), (ArrayList<GasBooking>) objects);
                    adapter = new TransactionDetailAdapter(getActivity(), transactionDetailList);
                    rvTransactionDetail.setAdapter(adapter);
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
                    param.put(AppController.USERS_INFO_ID, ParticularCustomerDetailInfo.CustomerId+"");
                    return param;
                }
            };

            queue.add(bm);

        }else{
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
