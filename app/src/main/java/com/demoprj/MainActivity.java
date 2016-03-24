package com.demoprj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demoprj.BaseRequest.RestModel.Login;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.User;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
//import com.parse.FindCallback;
//import com.parse.LogInCallback;
//import com.parse.ParseException;
//import com.parse.ParseInstallation;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtLoginUsername;
    EditText txtLoginPassword;
    TextInputLayout inputLoginPassword;
    TextInputLayout inputLoginUsername;

    Button btnLogin;
    ScrollView svLogin;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(ParseUser.getCurrentUser()!=null){
//            ParseUser user = ParseUser.getCurrentUser();
//            Intent next = new Intent();
//            if(user.getBoolean(AppConstant.IS_ADMIN)) {
//                next = new Intent(MainActivity.this, AdminHomeActivity.class);
//            }else{
//                next = new Intent(MainActivity.this, DeliveryboyHomeActivity.class);
//            }
//            startActivity(next);
//            finish();
//        }
        inputLoginPassword = (TextInputLayout) findViewById(R.id.inputLoginPassword);
        inputLoginUsername = (TextInputLayout) findViewById(R.id.inputLoginUsername);
        txtLoginPassword = (EditText) findViewById(R.id.txtLoginPassword);
        txtLoginUsername = (EditText) findViewById(R.id.txtLoginUsername);
        svLogin = (ScrollView) findViewById(R.id.svLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (txtLoginUsername.getText() == null || txtLoginUsername.getText().toString().length() <= 0) {
            Snackbar.make(svLogin, "Please enter Username", Snackbar.LENGTH_LONG).show();
            txtLoginUsername.requestFocus();
            inputLoginUsername.setError(getResources().getString(R.string.Field_require));
            inputLoginUsername.setErrorEnabled(true);
        } else if (txtLoginPassword.getText() == null || txtLoginPassword.getText().toString().length() <= 0) {
            inputLoginUsername.setErrorEnabled(false);
            txtLoginPassword.requestFocus();
            Snackbar.make(svLogin, "Please enter password", Snackbar.LENGTH_LONG).show();
            inputLoginPassword.setError(getResources().getString(R.string.Field_require));
        } else {
            inputLoginUsername.setErrorEnabled(false);
            inputLoginPassword.setErrorEnabled(false);
            if (Utils.isInternetAvailable(this)) {
                Utils.showProgressDialog(this, "Logging In");
                queue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                String url = AppController.mainUrl + AppController.API_LOGIN;

                baseModel<Login> bm = new baseModel(Request.Method.POST, url, Login.class, null, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Login loginResponse = (Login) response;
                        Intent next = new Intent();
                        if (loginResponse.getIsAdmin().toString().equals("1")) {
//                            Snackbar.make(svLogin, "Login Successful. Welcome Admin", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this,"Login Successful. Welcome Admin", Toast.LENGTH_SHORT ).show();
                            next = new Intent(MainActivity.this, AdminHomeActivity.class);
                        } else {
                            next = new Intent(MainActivity.this, DeliveryboyHomeActivity.class);
//                            Snackbar.make(svLogin, "Login Successful", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this,"Login Successful!", Toast.LENGTH_SHORT ).show();
                        }
                        startActivity(next);
                        finish();

                        Utils.hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response error", error.toString());
                        Utils.hideProgressDialog();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put(AppController.LOGIN_USERNAME, txtLoginUsername.getText().toString());
                        param.put(AppController.LOGIN_PASSWORD, txtLoginPassword.getText().toString());
                        return param;
                    }
                };

                queue.add(bm);
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response ", response);
//                        if (response.toString().equals("200")) {
//                            Snackbar.make(svLogin, "Login Successful", Snackbar.LENGTH_LONG).show();
//                            Intent next = new Intent();
//                            /*if(user.getBoolean(AppConstant.IS_ADMIN)) {
//                                next = new Intent(MainActivity.this, AdminHomeActivity.class);
//                            }else{
//                                next = new Intent(MainActivity.this, DeliveryboyHomeActivity.class);
//                            }*/
//                            startActivity(next);
//                            finish();
//                        }
//                        Utils.hideProgressDialog();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("response error", error.toString());
//                        Utils.hideProgressDialog();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//                        param.put(AppController.LOGIN_USERNAME, txtLoginUsername.getText().toString());
//                        param.put(AppController.LOGIN_PASSWORD, txtLoginPassword.getText().toString());
//                        return param;
//                    }
//                };
//                queue.add(stringRequest);
//                ParseUser.logInInBackground(txtLoginUsername.getText().toString(), txtLoginPassword.getText().toString(), new LogInCallback() {
//                    @Override
//                    public void done(ParseUser user, ParseException e) {
//                        Utils.hideProgressDialog();
//                        if (e == null) {
//                            Intent next = new Intent();
//                            Snackbar.make(svLogin, "Login Successful", Snackbar.LENGTH_LONG).show();
//                            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//                            if(user.getBoolean(AppConstant.IS_ADMIN)) {
//                                next = new Intent(MainActivity.this, AdminHomeActivity.class);
//                            }else{
//                                next = new Intent(MainActivity.this, DeliveryboyHomeActivity.class);
//                            }
//                            startActivity(next);
//                            finish();
//                            installation.put(AppConstant.INSTALLATION_USER_ID, ParseObject.createWithoutData("_User", user.getObjectId()));
//                            installation.saveInBackground(new SaveCallback() {
//                                @Override
//                                public void done(ParseException e) {
//                                    if (e == null) {
//
//                                    } else {
//
//                                    }
//                                }
//                            });
//                        } else {
//                            ParseQuery<User> userLoginQuery = ParseQuery.getQuery(User.class);
//                            userLoginQuery.findInBackground(new FindCallback<User>() {
//                                @Override
//                                public void done(List<User> objects, ParseException e) {
//                                    if(e==null){
//                                        if(objects.size()>0){
//
//                                        }
//                                    }else{
//
//                                    }
//                                }
//                            });
//                            Snackbar.make(svLogin, e.getMessage(), Snackbar.LENGTH_LONG).show();
//                        }
//                    }
//                });
            } else {
                Toast.makeText(this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }
        }
    }
}
