package com.demoprj.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demoprj.Adapter.CustomerDetailAdapter;
import com.demoprj.AdminHomeActivity;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.GsonModel.CustomerDetail;
import com.demoprj.GsonModel.DeliverdBottelDetail;
import com.demoprj.GsonModel.PACK.CustomerDetailModle;
import com.demoprj.ParseModel.User;
import com.demoprj.R;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerDetailsFragment extends BaseFragment implements View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    static RecyclerView rvCustomerDetail;
    static RecyclerView.Adapter adapter ;
    RecyclerView.LayoutManager mLayoutManager;
    static ProgressDialog progressDialog;
    FloatingActionButton fabAddCustomer;
    public static LinearLayout llSearch;
    public static EditText txtSearch;
    ImageButton ibSearchClose;
    Button btnSearch;
    int searchPosition = 0;
    public static ArrayList<CustomerDetailModle> UserList = new ArrayList<>();
    ViewGroup mainContainer;
    InputMethodManager imm;
    static Context ctx;
//    SwipeRefreshLayout swiperefreshAdmin;
    @Override
    public void onStart() {
        super.onStart();
        if(mainContainer!=null && imm!=null)
            imm.hideSoftInputFromWindow(mainContainer.getWindowToken(), 0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        //
        View rootView = inflater.inflate(R.layout.fragment_customer_details, container, false);
        ctx = getActivity();
        mainContainer = container;
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        rvCustomerDetail = (RecyclerView) rootView.findViewById(R.id.rvCustomerDetail);
//        swiperefreshAdmin = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshAdmin);
        llSearch = (LinearLayout) rootView.findViewById(R.id.llSearch);
        txtSearch = (EditText) rootView.findViewById(R.id.txtSearch);
        ibSearchClose = (ImageButton) rootView.findViewById(R.id.ibSearchClose);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
        fabAddCustomer = (FloatingActionButton) rootView.findViewById(R.id.fabAddCustomer);
//        fabAddCustomer.setOnClickListener(this);
        rvCustomerDetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvCustomerDetail.setLayoutManager(mLayoutManager);
        ibSearchClose.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
//        swiperefreshAdmin.setOnRefreshListener(this);
        txtSearch.addTextChangedListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserList.size()>0){
            adapter = new CustomerDetailAdapter(getActivity(), UserList);
            rvCustomerDetail.setAdapter(adapter);
        }else{
            LoadCustomerInfo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibSearchClose:
                txtSearch.setText("");
                llSearch.setVisibility(View.GONE);
                AdminHomeActivity.isSearchVisible = false;
                break;
            case R.id.btnSearch:
                PopupMenu pop = new PopupMenu(getActivity(),btnSearch);
                pop.getMenuInflater().inflate(R.menu.menu_search_popup, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.search_By_Id:
                                searchPosition = 0;
                                txtSearch.setText("");
                                txtSearch.setHint(getResources().getString(R.string.Search_By_Id));
                                btnSearch.setText(getResources().getString(R.string.By_Id));
                                break;
                            case R.id.search_By_Name:
                                searchPosition = 1;
                                txtSearch.setText("");
                                txtSearch.setHint(getResources().getString(R.string.Search_By_Name));
                                btnSearch.setText(getResources().getString(R.string.By_Name));
                                break;
                            case R.id.search_By_Address:
                                searchPosition = 2;
                                txtSearch.setText("");
                                txtSearch.setHint(getResources().getString(R.string.Search_By_Address));
                                btnSearch.setText(getResources().getString(R.string.By_Address));
                                break;
                        }
                        return false;
                    }

                });

                pop.show();//showing popup menu
        break;
        }
//        Intent next = new Intent(getActivity(), AddCustomerDetail.class);
//        startActivity(next);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ArrayList<CustomerDetailModle> searchUserList = new ArrayList<>();
        if(searchPosition==0) {
            for (CustomerDetailModle d : UserList) {
                    if (String.valueOf(d.getCustomerId()).contains(txtSearch.getText().toString())) {
                        searchUserList.add(d);
                    }
            }
        }else if(searchPosition==1){
            for (CustomerDetailModle d : UserList) {
                String name = d.getFullName();
                if (name.toLowerCase().contains(txtSearch.getText().toString().toLowerCase())) {
                    searchUserList.add(d);
                }
            }

        }else if(searchPosition==2){
            for (CustomerDetailModle d : UserList) {
                if (d.getAddress().toLowerCase().contains(txtSearch.getText().toString().toLowerCase())) {
                    searchUserList.add(d);
                }
            }
        }
        adapter = new CustomerDetailAdapter(getActivity(), searchUserList);
        rvCustomerDetail.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        LoadCustomerInfo();
    }

    public static void LoadCustomerInfo() {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Fetching Customer Detail");
        progressDialog.setMessage("Please wait");
        if(Utils.isInternetAvailable(ctx)) {
            progressDialog.show();
            /*ParseQuery<User> userListQuery = ParseQuery.getQuery(User.class);
            userListQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            if (UserList.size() > 0) {
                                UserList.clear();
                            }
                            UserList.addAll(objects);
                            adapter = new CustomerDetailAdapter(ctx, (ArrayList<User>) objects);
                            rvCustomerDetail.setAdapter(adapter);
                        }
                    } else {

                    }
                    if (progressDialog != null)
                        progressDialog.hide();
//                    swiperefreshAdmin.setRefreshing(false);
                }

            });*/
            String url = AppController.mainUrl+AppController.API_GETCUSTOMER;
            RequestQueue queue = CustomVolleyRequestQueue.getInstance(ctx.getApplicationContext()).getRequestQueue();
            baseModel bm = new baseModel(Request.Method.GET,url,null, new TypeToken<List<CustomerDetailModle>>(){}.getType(), new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Log.e("response", response.toString());
                    if (progressDialog != null)
                        progressDialog.hide();
                    UserList.addAll((ArrayList<CustomerDetailModle>) response);
                    adapter = new CustomerDetailAdapter(ctx, (ArrayList<CustomerDetailModle>) response);
                    rvCustomerDetail.setAdapter(adapter);
//                    swiperefreshAdmin.setRefreshing(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response error",error.toString());
                    if (progressDialog != null)
                        progressDialog.hide();
//                    swiperefreshAdmin.setRefreshing(false);
                }
            });

            queue.add(bm);
        }else {
//            swiperefreshAdmin.setRefreshing(false);
            Toast.makeText(ctx, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }
}