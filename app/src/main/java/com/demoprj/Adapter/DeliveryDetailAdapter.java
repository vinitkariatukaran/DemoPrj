package com.demoprj.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demoprj.BaseRequest.RestModel.TransactionDetail;
import com.demoprj.Constant.AppConstant;
import com.demoprj.Fragment.DeliveryDetailsFragment;
import com.demoprj.ParseModel.DeliveryModel;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.R;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.demoprj.Utils.Utils;
import com.demoprj.ParticularDeliveryDetail;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mounil on 13/12/2015.
 */
public class DeliveryDetailAdapter extends RecyclerView.Adapter<DeliveryDetailAdapter.ViewHolder> {

    //    ArrayList<GasBooking> userList = new ArrayList<>();
    Context context;
    List<TransactionDetail> transactionDetailList;
    int fromWhere;
//    public DeliveryDetailAdapter(Context context, ArrayList<GasBooking> userList,int fromWhere){
//        this.context = context;
//        this.userList = userList;
//        this.fromWhere = fromWhere;
//    }

    public DeliveryDetailAdapter(Context context , List<TransactionDetail> transactionDetailList, int fromWhere) {
        this.context = context;
        this.transactionDetailList = transactionDetailList;
        this.fromWhere = fromWhere;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_delivery_detail_info, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final DeliveryModel Dm = new DeliveryModel();
        final TransactionDetail details = transactionDetailList.get(i);
//        User u = (User) userList.get(i).get(AppConstant.GASBOOKING_USER_ID);
        if (Utils.isInternetAvailable(context)) {
//            ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
//            userQuery.whereEqualTo(AppConstant.OBJECT_ID, u.getObjectId());
//            userQuery.findInBackground(new FindCallback<User>() {
//                @Override
//                public void done(List<User> objects, ParseException e) {
//                    if (e == null) {
//                        if (objects.size() > 0) {
            viewHolder.lblDeliveryCustomerId.setText(details.getUserInfoId() + "");
            viewHolder.lblDeliveryCustomerName.setText(details.getFullName());
//                            Dm.setName(u.getFirstName() + " " + u.getLastName());
//                            Dm.setCustomerId(u.getCustomerId());
            viewHolder.lblDeliveryCustomerLandmark.setText(details.getLandmark());
//                            Dm.setLandmark(u.getLandmark());
            if (!details.getIsService().equals(1)) {
//                                final GasType gasType = (GasType) u.get(AppConstant.USER_GAS_TYPE_ID);
//                                ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
//                                gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasType.getObjectId());
//                                gasTypeQuery.findInBackground(new FindCallback<GasType>() {
//                                    @Override
//                                    public void done(List<GasType> objects, ParseException e) {
//                                        if (e == null) {
//                                            if (objects.size() > 0) {
                viewHolder.lblDeliveryGasType.setText(details.getGasType());
//                                                Dm.setType(objects.get(0).getGasType());
//                                            }
//                                        } else {
//
//                                        }
//                                    }
//                                });
            }

//                        }
//                    }
//                }
//            });
//            if (!details.getDateOfDelivery().equals("0000-00-00 00:00:00"))
            boolean deleiveryStatus = details.getDateOfDelivery().equals("0000-00-00 00:00:00");
            viewHolder.chbDeliverUndeliver.setChecked(!deleiveryStatus);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm aa");
            if (!deleiveryStatus) {
                Date DeliveryDate = getDate(details.getDateOfDelivery());
                Date BookingDate = getDate(details.getDateOfBooking());
                Log.e("Booking & deliverDate", BookingDate + "    :::::    " + DeliveryDate);
                viewHolder.llDeliveryDate.setVisibility(View.VISIBLE);
                viewHolder.lblDeliveryCustomerBDate.setText(sdf.format(BookingDate));
                viewHolder.lblDeliveryCustomerDDate.setText(sdf.format(DeliveryDate));
            } else {
                Date BookingDate1 = getDate(details.getDateOfBooking());
                Log.e("Booking ", BookingDate1 + "");
                viewHolder.lblDeliveryCustomerBDate.setText(sdf.format(BookingDate1));
                viewHolder.llDeliveryDate.setVisibility(View.GONE);
            }

            if (details.getIsService().equals(1)) {
                viewHolder.llDeliveryServiceCharge.setVisibility(View.VISIBLE);
                viewHolder.llDeliveryServiceNote.setVisibility(View.VISIBLE);
                viewHolder.ivService.setVisibility(View.VISIBLE);
                viewHolder.llDeliverySizePrice.setVisibility(View.GONE);
                viewHolder.llDeliveryTypeQuantity.setVisibility(View.GONE);
                viewHolder.llDeliveryChargeTotal.setVisibility(View.GONE);
                viewHolder.lblDeliveryServiceCharge.setText(details.getServiceCharge());
//                Dm.setServiceCharge(details.getServiceCharge());
                viewHolder.lblDeliveryServiceNote.setText(details.getServiceNote() + "");
//                Dm.setServiceNote(details.getServiceNote());
            } else {
                double total = Double.parseDouble(details.getTotal());
                if  (!details.getDeliveryCharge().equals("")) {
                    double charge =  (double)Integer.parseInt(details.getDeliveryCharge());
                    viewHolder.lblDeliveryGasPrice.setText((total - charge) + "");
                    viewHolder.lblDeliveryCharge.setText(charge + "");
                    viewHolder.lblDeliveryTotal.setText(total + "");
                } else {
                    viewHolder.lblDeliveryGasPrice.setText(total + "");
                    viewHolder.lblDeliveryCharge.setText("0");
                    viewHolder.lblDeliveryTotal.setText(total + "");
                }
                viewHolder.llDeliveryServiceCharge.setVisibility(View.GONE);
                viewHolder.llDeliveryServiceNote.setVisibility(View.GONE);
                viewHolder.ivService.setVisibility(View.GONE);
                viewHolder.llDeliverySizePrice.setVisibility(View.VISIBLE);
                viewHolder.llDeliveryTypeQuantity.setVisibility(View.VISIBLE);
                viewHolder.llDeliveryChargeTotal.setVisibility(View.VISIBLE);
//                Dm.setPrice(userList.get(i).getTotal());
//                GasSize gasSize = (GasSize) userList.get(i).get(AppConstant.GASBOOKING_GAS_SIZE_ID);
//                ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
//                if (gasSize != null) {
//                        gasSizeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasSize.getObjectId());
//                        gasSizeQuery.findInBackground(new FindCallback<GasSize>() {
//                            @Override
//                            public void done(List<GasSize> objects, ParseException e) {
//                                if (e == null) {
//                                    if (objects.size() > 0) {
                    viewHolder.lblDeliveryGasSize.setText(details.getGasSize() + "");
//                    Dm.setSize(gasSize.getGasSize());
//                                    }
//                                } else {
//
//                                }
//                            }
//                        });
//                }
//            viewHolder.lblDeliveryGasSize.setText(gasSize.getGasSize()+"");
                viewHolder.lblDeliveryGasQuantity.setText(details.getQuantity() + "");
//                Dm.setObjectId(userList.get(i).getObjectId());
//                Dm.setQuantity(userList.get(i).getQuantity());
//                DeliveryDetailsFragment.DeliveryList.add(Dm);
            }
        } else {
            Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    private Date getDate(String dateOfDelivery) {
//        0000-00-00 00:00:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(dateOfDelivery);
            System.out.println(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getItemCount() {
        return transactionDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        public TextView lblDeliveryCustomerId;
        public TextView lblDeliveryCustomerName;
        public TextView lblDeliveryCustomerLandmark;
        public TextView lblDeliveryGasType;
        public TextView lblDeliveryGasPrice;
        public TextView lblDeliveryGasSize;
        public TextView lblDeliveryGasQuantity;
        public TextView lblDeliveryServiceNote;
        public TextView lblDeliveryServiceCharge;
        public TextView lblDeliveryCustomerBDate;
        public TextView lblDeliveryCustomerDDate;
        public TextView lblDeliveryCharge;
        public TextView lblDeliveryTotal;

        public LinearLayout llDeliveryDate;
        public LinearLayout llDeliveryTypeQuantity;
        public LinearLayout llDeliverySizePrice;
        public LinearLayout llDeliveryServiceNote;
        public LinearLayout llDeliveryServiceCharge;
        public LinearLayout llDeliveryChargeTotal;
        public ImageView ivService;
        public CheckBox chbDeliverUndeliver;


        public ViewHolder(View itemView) {
            super(itemView);
            llDeliveryDate = (LinearLayout) itemView.findViewById(R.id.llDeliveryDate);
            llDeliveryChargeTotal = (LinearLayout) itemView.findViewById(R.id.llDeliveryChargeTotal);
            llDeliveryTypeQuantity = (LinearLayout) itemView.findViewById(R.id.llDeliveryTypeQuantity);
            llDeliverySizePrice = (LinearLayout) itemView.findViewById(R.id.llDeliverySizePrice);
            llDeliveryServiceNote = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceNote);
            llDeliveryServiceCharge = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceCharge);
            chbDeliverUndeliver = (CheckBox) itemView.findViewById(R.id.chbDeliverUndeliver);

            ivService = (ImageView) itemView.findViewById(R.id.ivService);

            lblDeliveryTotal = (TextView) itemView.findViewById(R.id.lblDeliveryTotal);
            lblDeliveryCharge = (TextView) itemView.findViewById(R.id.lblDeliveryCharge);
            lblDeliveryCustomerBDate = (TextView) itemView.findViewById(R.id.lblDeliveryCustomerBDate);
            lblDeliveryCustomerDDate = (TextView) itemView.findViewById(R.id.lblDeliveryCustomerDDate);
            lblDeliveryServiceCharge = (TextView) itemView.findViewById(R.id.lblDeliveryServiceCharge);
            lblDeliveryServiceNote = (TextView) itemView.findViewById(R.id.lblDeliveryServiceNote);
            lblDeliveryCustomerId = (TextView) itemView.findViewById(R.id.lblDeliveryCustomerId);
            lblDeliveryCustomerName = (TextView) itemView.findViewById(R.id.lblDeliveryCustomerName);
            lblDeliveryCustomerLandmark = (TextView) itemView.findViewById(R.id.lblDeliveryCustomerLandmark);
            lblDeliveryGasType = (TextView) itemView.findViewById(R.id.lblDeliveryGasType);
            lblDeliveryGasPrice = (TextView) itemView.findViewById(R.id.lblDeliveryGasPrice);
            lblDeliveryGasSize = (TextView) itemView.findViewById(R.id.lblDeliveryGasSize);
            lblDeliveryGasQuantity = (TextView) itemView.findViewById(R.id.lblDeliveryGasQuantity);
            itemView.setOnClickListener(this);
            chbDeliverUndeliver.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            if (Utils.isInternetAvailable(context)) {
                boolean deleiveryStatus = transactionDetailList.get(getPosition()).getDateOfDelivery().equals("0000-00-00 00:00:00");
                if (deleiveryStatus) {
                    Intent next = new Intent(context, ParticularDeliveryDetail.class);
                    TransactionDetail details = transactionDetailList.get(getPosition());
                    next.putExtra("details", details);
                    next.putExtra("ObjectId", 2+"");
                    context.startActivity(next);
                }
            } else {
                Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {}/*{
            if (userList.get(getPosition()).getDeliverySatus() != isChecked) {
                AlertDialog.Builder AD = new AlertDialog.Builder(context);
                AD.setTitle("Alert");
                AD.setCancelable(false);
                AD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setTitle("Saving Informaton");
                        progressDialog.setMessage("Please Wait");
                        if (Utils.isInternetAvailable(context)) {
                            progressDialog.show();
                            ParseQuery<GasBooking> gasBookingQuery = ParseQuery.getQuery(GasBooking.class);
                            gasBookingQuery.whereEqualTo(AppConstant.OBJECT_ID, userList.get(getPosition()).getObjectId());
                            gasBookingQuery.findInBackground(new FindCallback<GasBooking>() {
                                @Override
                                public void done(List<GasBooking> objects, ParseException e) {
                                    if (e == null) {
                                        if (objects.size() > 0) {
                                            objects.get(0).setDateOfDelivery(new Date());
                                            objects.get(0).setDeliverySatus(isChecked);
                                            objects.get(0).saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast.makeText(context, "Data successfully saved.", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    } else {

                                    }
                                    progressDialog.dismiss();
                                }
                            });

//                            userList.get(getPosition()).setisService(isChecked);
                            if (fromWhere == 1) {
                                userList.remove(getPosition());
                                DeliverySegment.adapter = new DeliveryDetailAdapter(context, userList, 1);
                                DeliverySegment.rvDeliveryDetail.setAdapter(DeliverySegment.adapter);
                            } else if (fromWhere == 2) {
                                userList.remove(getPosition());
                                UndeliverySegment.adapter = new DeliveryDetailAdapter(context, userList, 2);
                                UndeliverySegment.rvDeliveryDetail.setAdapter(UndeliverySegment.adapter);
                            }
                        } else {
                            Toast.makeText(context, "Internet is not Available. Please try again later.", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                AD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chbDeliverUndeliver.setChecked(!isChecked);
                        dialog.dismiss();
                    }
                });
                if (isChecked) {
                    AD.setMessage("Are you sure want to do mark this order Delivered");
                    AD.show();
                } else {
                    AD.setMessage("Are you sure want to do mark this order Undelivered");
                    AD.show();
                }
            }
        }*/
    }
}