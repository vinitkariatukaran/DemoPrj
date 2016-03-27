package com.demoprj.Fragment;


import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.demoprj.gsonmodels.pack.GasSizePrice;
import com.demoprj.BaseRequest.baseModel;
import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.R;
import com.demoprj.Utils.AppController;
import com.demoprj.Utils.CustomVolleyRequestQueue;
import com.demoprj.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceChangeFragment extends Fragment implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    RadioGroup rgPriceBookType;
    Spinner spnPriceBookGasSize;
    TextInputLayout inputPriceBookQuantity;
    EditText txtPriceBookQuantity;
    Button btnPriceChange;
    RadioButton selectedGasType;
    ProgressDialog progressDialog;
    NestedScrollView svPrice;
    boolean fromWhere = false;
    ArrayList<GasSize> gasSizeList = new ArrayList<GasSize>();
    GasSize selectedGasSize;
    String oldPrice;
    TextView lblDo1;
    TextView lblDo1Price;
    TextView lblDo2;
    TextView lblDo2Price;
    TextView lblDo3;
    TextView lblDo3Price;
    TextView lblDo4;
    TextView lblDo4Price;
    TextView lblCo1;
    TextView lblCo1Price;
    TextView lblCo2;
    TextView lblCo2Price;
    TextView lblCo3;
    TextView lblCo3Price;

    public PriceChangeFragment() {
        // Required empty public constructor
    }

    //    Vinit K
    ViewGroup mainContainer;
    InputMethodManager imm;
    LinearLayout tablePrice;

    List<GasSizePrice> gasSizePriceList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();

        hideKeyboard();
    }

    private void hideKeyboard() {
        if (mainContainer != null && imm != null)
            imm.hideSoftInputFromWindow(mainContainer.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_price_change, container, false);
        mainContainer = container;
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait");
        inputPriceBookQuantity = (TextInputLayout) rootView.findViewById(R.id.inputPriceBookQuantity);
        lblDo1 = (TextView) rootView.findViewById(R.id.lblDo1);
        lblDo1Price = (TextView) rootView.findViewById(R.id.lblDo1Price);
        lblDo2 = (TextView) rootView.findViewById(R.id.lblDo2);
        lblDo2Price = (TextView) rootView.findViewById(R.id.lblDo2Price);
        lblDo3 = (TextView) rootView.findViewById(R.id.lblDo3);
        lblDo3Price = (TextView) rootView.findViewById(R.id.lblDo3Price);
        lblDo4 = (TextView) rootView.findViewById(R.id.lblDo4);
        lblDo4Price = (TextView) rootView.findViewById(R.id.lblDo4Price);
        lblCo1 = (TextView) rootView.findViewById(R.id.lblCo1);
        lblCo1Price = (TextView) rootView.findViewById(R.id.lblCo1Price);
        lblCo2 = (TextView) rootView.findViewById(R.id.lblCo2);
        lblCo2Price = (TextView) rootView.findViewById(R.id.lblCo2Price);
        lblCo3 = (TextView) rootView.findViewById(R.id.lblCo3);
        lblCo3Price = (TextView) rootView.findViewById(R.id.lblCo3Price);

        tablePrice = (LinearLayout) rootView.findViewById(R.id.tablePrice);

        txtPriceBookQuantity = (EditText) rootView.findViewById(R.id.txtPriceBookQuantity);
        svPrice = (NestedScrollView) rootView.findViewById(R.id.svPrice);

        rgPriceBookType = (RadioGroup) rootView.findViewById(R.id.rgPriceBookType);
        selectedGasType = (RadioButton) rgPriceBookType.findViewById(rgPriceBookType.getCheckedRadioButtonId());

        spnPriceBookGasSize = (Spinner) rootView.findViewById(R.id.spnPriceBookGasSize);

        btnPriceChange = (Button) rootView.findViewById(R.id.btnPriceChange);
        tablePrice.setVisibility(View.INVISIBLE);
        FetchiGasSize();
        btnPriceChange.setOnClickListener(this);
        if (!fromWhere)
            SettingGasSize(1, 1);
        spnPriceBookGasSize.setOnItemSelectedListener(this);

        rgPriceBookType.setOnCheckedChangeListener(this);
        return rootView;
    }

    private void FetchiGasSize() {
        if (Utils.isInternetAvailable(getActivity())) {
//            ParseQuery<GasSize> gasSizeParseQuery = ParseQuery.getQuery(GasSize.class);
//            gasSizeParseQuery.orderByAscending(AppConstant.USER_GAS_TYPE_ID);
//            gasSizeParseQuery.findInBackground(new FindCallback<GasSize>() {
//                @Override
//                public void done(List<GasSize> objects, ParseException e) {
//                    if(e==null){
//                        if(objects.size()>0){
//                            lblDo1.setText(objects.get(0).getGasSize()+"");
//                            lblDo1Price.setText(objects.get(0).getPrice()+"");
//                            lblDo2.setText(objects.get(1).getGasSize()+"");
//                            lblDo2Price.setText(objects.get(1).getPrice()+"");
//                            double size = objects.get(2).getGasSize();
//                            float size1 = Float.parseFloat(objects.get(2).getGasSize()+"");
//                            lblDo3.setText(objects.get(2).getGasSize()+"");
//                            lblDo3Price.setText(objects.get(2).getPrice()+"");
//                            lblDo4.setText(objects.get(3).getGasSize()+"");
//                            lblDo4Price.setText(objects.get(3).getPrice()+"");
//                            lblCo1.setText(objects.get(4).getGasSize()+"");
//                            lblCo1Price.setText(objects.get(4).getPrice()+"");
//                            lblCo2.setText(objects.get(5).getGasSize()+"");
//                            lblCo2Price.setText(objects.get(5).getPrice()+"");
//                            lblCo3.setText(objects.get(6).getGasSize()+"");
//                            lblCo3Price.setText(objects.get(6).getPrice()+"");
//                            tablePrice.setVisibility(View.VISIBLE);
//                        }
//                    }else{
//
//                    }
//                }
//            });

            RequestQueue queue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
            String url = AppController.mainUrl + AppController.API_GET_GAS_SIZE;
            Type type = new TypeToken<List<GasSizePrice>>() {
            }.getType();
            baseModel<String> bm = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Log.e("response", response.toString());
                    gasSizePriceList.clear();
                    gasSizePriceList = (List<GasSizePrice>) response;
//                    progressDialog.dismiss();
                    secondSync();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response error", error.toString());
//                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put(AppController.GAS_TYPE_ID, 1 + "");
                    return param;
                }
            };
            queue.add(bm);
        } else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    private void secondSync() {
        RequestQueue queue1 = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        String url = AppController.mainUrl + AppController.API_GET_GAS_SIZE;
        Type type = new TypeToken<List<GasSizePrice>>() {
        }.getType();
        baseModel bm1 = new baseModel(Request.Method.POST, url, null, type, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.e("response", response.toString());
//                    gasSizePriceList.clear();
                List<GasSizePrice> gasList = (List<GasSizePrice>) response;
                gasSizePriceList.addAll(gasList);
                lblDo1.setText(gasSizePriceList.get(0).getGasSize() + "");
                lblDo1Price.setText(gasSizePriceList.get(0).getPrice() + "");
//                    lblDo2.setText(objects.get(1).getGasSize() + "");
//                    lblDo2Price.setText(objects.get(1).getPrice() + "");
//                    double size = objects.get(2).getGasSize();
//                    float size1 = Float.parseFloat(objects.get(2).getGasSize() + "");
//                    lblDo3.setText(objects.get(2).getGasSize() + "");
//                    lblDo3Price.setText(objects.get(2).getPrice() + "");
//                    lblDo4.setText(objects.get(3).getGasSize() + "");
//                    lblDo4Price.setText(objects.get(3).getPrice() + "");
//                    lblCo1.setText(objects.get(4).getGasSize() + "");
//                    lblCo1Price.setText(objects.get(4).getPrice() + "");
//                    lblCo2.setText(objects.get(5).getGasSize() + "");
//                    lblCo2Price.setText(objects.get(5).getPrice() + "");
//                    lblCo3.setText(objects.get(6).getGasSize() + "");
//                    lblCo3Price.setText(objects.get(6).getPrice() + "");
                tablePrice.setVisibility(View.VISIBLE);
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
                param.put(AppController.GAS_TYPE_ID, 2 + "");
                return param;
            }
        };
        queue1.add(bm1);
    }

    private void SettingGasSize(int GasTypeId, final int showProgress) {
        if (Utils.isInternetAvailable(getActivity())) {
            if (showProgress == 1) {
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
                            spnPriceBookGasSize.setAdapter(adapter);
                            spnPriceBookGasSize.setSelection(0);
                        }
                        if (showProgress == 1)
                            if (progressDialog != null)
                                progressDialog.hide();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (txtPriceBookQuantity.getText() == null || txtPriceBookQuantity.getText().length() <= 0) {
            Snackbar.make(svPrice, "Please enter Price", Snackbar.LENGTH_LONG).show();
            txtPriceBookQuantity.requestFocus();
            inputPriceBookQuantity.setError(getResources().getString(R.string.Field_require));
            inputPriceBookQuantity.setErrorEnabled(true);
        } else if (oldPrice.equals(txtPriceBookQuantity.getText().toString())) {
            Snackbar.make(svPrice, "You have not change price till now", Snackbar.LENGTH_LONG).show();
        } else {
            DisplayAlertDialog();
        }
    }

    private void DisplayAlertDialog() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Alert");
        dialog.setMessage("Are you sure want to change price");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Utils.isInternetAvailable(getActivity())) {
                    ParseQuery<GasSize> gasSizeChangeQuery = ParseQuery.getQuery(GasSize.class);
                    gasSizeChangeQuery.whereEqualTo(AppConstant.OBJECT_ID, selectedGasSize.getObjectId());
                    gasSizeChangeQuery.findInBackground(new FindCallback<GasSize>() {
                        @Override
                        public void done(List<GasSize> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    objects.get(0).setPrice(Double.parseDouble(txtPriceBookQuantity.getText().toString()));
                                    objects.get(0).saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                oldPrice = txtPriceBookQuantity.getText().toString();
                                                Snackbar.make(svPrice, "Your Price Change Successfully", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Snackbar.make(svPrice, "Some error occured while saving your price", Snackbar.LENGTH_LONG).show();
                                                txtPriceBookQuantity.setText(oldPrice);
                                            }
                                        }
                                    });
                                }
                                tablePrice.setVisibility(View.INVISIBLE);
                                FetchiGasSize();
                                hideKeyboard();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
        if (progressDialog != null)
            progressDialog.hide();

        fromWhere = true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedGasSize = gasSizeList.get(position);
        oldPrice = gasSizeList.get(position).getPrice() + "";
        txtPriceBookQuantity.setText(gasSizeList.get(position).getPrice() + "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rbPriceBookCommercial:
                if (!fromWhere)
                    SettingGasSize(2, 0);
                break;
            case R.id.rbPriceBookDomestic:
                if (!fromWhere)
                    SettingGasSize(1, 0);
                break;
        }
        fromWhere = false;
    }

public void refresh() {
    }
}