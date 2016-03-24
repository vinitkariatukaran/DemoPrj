package com.demoprj.DeliveryBoyApp.AdapterDeliveryBoy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.demoprj.Constant.AppConstant;
import com.demoprj.DeliveryBoyApp.ParticularDeliveryDetailActivityofDeliveryBoy;
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
public class DeliveryDetailAdapterofDeliveryBoy extends RecyclerView.Adapter<DeliveryDetailAdapterofDeliveryBoy.ViewHolder> {

    ArrayList<GasBooking> userList = new ArrayList<>();
    Context context;
    int fromWhere;
    public DeliveryDetailAdapterofDeliveryBoy(Context context, ArrayList<GasBooking> userList, int fromWhere){
        this.context = context;
        this.userList = userList;
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
        viewHolder.lblDeliveryGasPrice.setVisibility(View.GONE);
        viewHolder.lblDeliveryCharge.setVisibility(View.GONE);
        viewHolder.llDeliveryChargeTotal.setVisibility(View.GONE);
        User u = (User) userList.get(i).get(AppConstant.GASBOOKING_USER_ID);
        viewHolder.chbDeliverUndeliver.setVisibility(View.GONE);
        if(Utils.isInternetAvailable(context)) {
//            ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
//            userQuery.whereEqualTo(AppConstant.OBJECT_ID, u.getObjectId());
//            userQuery.findInBackground(new FindCallback<User>() {
//                @Override
//                public void done(List<User> objects, ParseException e) {
//                    if (e == null) {
//                        if (objects.size() > 0) {
                            viewHolder.lblDeliveryCustomerId.setText(u.getCustomerId() + "");
                            viewHolder.lblDeliveryCustomerName.setText(u.getFirstName() + " " + u.getLastName());
                            Dm.setName(u.getFirstName() + " " + u.getLastName());
                            Dm.setCustomerId(u.getCustomerId());
                            viewHolder.lblDeliveryCustomerLandmark.setText(u.getLandmark());
                            Dm.setLandmark(u.getLandmark());
                            if (!userList.get(i).getisService()) {
                                final GasType gasType = (GasType) u.get(AppConstant.USER_GAS_TYPE_ID);
                                ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
                                gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasType.getObjectId());
                                gasTypeQuery.findInBackground(new FindCallback<GasType>() {
                                    @Override
                                    public void done(List<GasType> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() > 0) {
                                                viewHolder.lblDeliveryGasType.setText(objects.get(0).getGasType());
                                                Dm.setType(objects.get(0).getGasType());
                                            }
                                        } else {

                                        }
                                    }
                                });
                            }

//                        }
//                    }
//                }
//            });
            viewHolder.chbDeliverUndeliver.setChecked(userList.get(i).getDeliverySatus());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm aa");
            if(userList.get(i).getDeliverySatus()){
                Date DeliveryDate = userList.get(i).getDateOfDelivery();
                Date BookingDate = userList.get(i).getDateOfBooking();
                Log.e("Booking & deliverDate",BookingDate+"    :::::    "+DeliveryDate);
                viewHolder.llDeliveryDate.setVisibility(View.VISIBLE);
                viewHolder.lblDeliveryCustomerBDate.setText(sdf.format(BookingDate));
                viewHolder.lblDeliveryCustomerDDate.setText(sdf.format(DeliveryDate));
            }else{
                Date BookingDate1 = userList.get(i).getDateOfBooking();
                Log.e("Booking ",BookingDate1+"");
                viewHolder.lblDeliveryCustomerBDate.setText(sdf.format(BookingDate1));
                viewHolder.llDeliveryDate.setVisibility(View.GONE);
            }

            if (userList.get(i).getisService()) {
                viewHolder.llDeliveryServiceCharge.setVisibility(View.VISIBLE);
                viewHolder.llDeliveryServiceNote.setVisibility(View.VISIBLE);
                viewHolder.ivService.setVisibility(View.VISIBLE);
                viewHolder.llDeliverySizePrice.setVisibility(View.GONE);
                viewHolder.llDeliveryChargeTotal.setVisibility(View.GONE);
                viewHolder.llDeliveryTypeQuantity.setVisibility(View.GONE);
                viewHolder.lblDeliveryServiceCharge.setText(userList.get(i).getServiceCharge());
                Dm.setServiceCharge(userList.get(i).getServiceCharge());
                viewHolder.lblDeliveryServiceNote.setText(userList.get(i).getServiceNote());
                Dm.setServiceNote(userList.get(i).getServiceNote());
            } else {
                if(userList.get(i).has(AppConstant.GASBOOKING_DELIVERY_CHARGE) && (!userList.get(i).getDeliveryCharge().equals(""))) {
                    viewHolder.lblDeliveryGasPrice.setText((userList.get(i).getTotal() - (Double.parseDouble(userList.get(i).getDeliveryCharge()))) + "");
                    viewHolder.lblDeliveryCharge.setText(userList.get(i).getDeliveryCharge() + "");
                    viewHolder.lblDeliveryTotal.setText(userList.get(i).getTotal() + "");
                }else{
                    viewHolder.lblDeliveryGasPrice.setText(userList.get(i).getTotal() + "");
                    viewHolder.lblDeliveryCharge.setText("0");
                    viewHolder.lblDeliveryTotal.setText(userList.get(i).getTotal()+"");
                }
                viewHolder.llDeliveryServiceCharge.setVisibility(View.GONE);
                viewHolder.llDeliveryServiceNote.setVisibility(View.GONE);
                viewHolder.ivService.setVisibility(View.GONE);
                viewHolder.llDeliverySizePrice.setVisibility(View.VISIBLE);
//                viewHolder.llDeliveryChargeTotal.setVisibility(View.VISIBLE);
                viewHolder.llDeliveryTypeQuantity.setVisibility(View.VISIBLE);
                Dm.setPrice(userList.get(i).getTotal());
                GasSize gasSize = (GasSize) userList.get(i).get(AppConstant.GASBOOKING_GAS_SIZE_ID);
                ParseQuery<GasSize> gasSizeQuery = ParseQuery.getQuery(GasSize.class);
                if(gasSize!=null) {
//                        gasSizeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasSize.getObjectId());
//                        gasSizeQuery.findInBackground(new FindCallback<GasSize>() {
//                            @Override
//                            public void done(List<GasSize> objects, ParseException e) {
//                                if (e == null) {
//                                    if (objects.size() > 0) {
                                        viewHolder.lblDeliveryGasSize.setText(gasSize.getGasSize() + "");
                                        Dm.setSize(gasSize.getGasSize());
//                                    }
//                                } else {
//
//                                }
//                            }
//                        });
                }
//            viewHolder.lblDeliveryGasSize.setText(gasSize.getGasSize()+"");
                viewHolder.lblDeliveryGasQuantity.setText(userList.get(i).getQuantity() + "");
                Dm.setObjectId(userList.get(i).getObjectId());
                Dm.setQuantity(userList.get(i).getQuantity());
                DeliveryDetailsFragment.DeliveryList.add(Dm);
            }
        }else {
            Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
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

        public LinearLayout llDeliveryChargeTotal;
        public LinearLayout llDeliveryDate;
        public LinearLayout llDeliveryTypeQuantity;
        public LinearLayout llDeliverySizePrice;
        public LinearLayout llDeliveryServiceNote;
        public LinearLayout llDeliveryServiceCharge;
        public ImageView ivService;
        public CheckBox chbDeliverUndeliver;


        public ViewHolder(View itemView) {
            super(itemView);
            llDeliveryDate = (LinearLayout) itemView.findViewById(R.id.llDeliveryDate);
            llDeliveryTypeQuantity = (LinearLayout) itemView.findViewById(R.id.llDeliveryTypeQuantity);
            llDeliverySizePrice = (LinearLayout) itemView.findViewById(R.id.llDeliverySizePrice);
            llDeliveryServiceNote = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceNote);
            llDeliveryServiceCharge = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceCharge);
            llDeliveryChargeTotal = (LinearLayout) itemView.findViewById(R.id.llDeliveryChargeTotal);

            chbDeliverUndeliver = (CheckBox) itemView.findViewById(R.id.chbDeliverUndeliver);

            ivService = (ImageView) itemView.findViewById(R.id.ivService);

            lblDeliveryTotal = (TextView)itemView.findViewById(R.id.lblDeliveryTotal);
            lblDeliveryCharge = (TextView)itemView.findViewById(R.id.lblDeliveryCharge);
            lblDeliveryCustomerBDate = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerBDate);
            lblDeliveryCustomerDDate = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerDDate);
            lblDeliveryServiceCharge = (TextView)itemView.findViewById(R.id.lblDeliveryServiceCharge);
            lblDeliveryServiceNote = (TextView)itemView.findViewById(R.id.lblDeliveryServiceNote);
            lblDeliveryCustomerId = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerId);
            lblDeliveryCustomerName = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerName);
            lblDeliveryCustomerLandmark = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerLandmark);
            lblDeliveryGasType = (TextView)itemView.findViewById(R.id.lblDeliveryGasType);
            lblDeliveryGasPrice = (TextView)itemView.findViewById(R.id.lblDeliveryGasPrice);
            lblDeliveryGasSize = (TextView)itemView.findViewById(R.id.lblDeliveryGasSize);
            lblDeliveryGasQuantity = (TextView)itemView.findViewById(R.id.lblDeliveryGasQuantity);
            lblDeliveryGasPrice.setVisibility(View.GONE);
            lblDeliveryCharge.setVisibility(View.GONE);
            llDeliveryChargeTotal.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
            chbDeliverUndeliver.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            if(Utils.isInternetAvailable(context)) {
                Intent next = new Intent(context, ParticularDeliveryDetailActivityofDeliveryBoy.class);
                next.putExtra("ObjectId", userList.get(getPosition()).getObjectId());
                context.startActivity(next);
            }else {
                Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
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
                                DeliverySegment.adapter = new DeliveryDetailAdapterofDeliveryBoy(context, userList, 1);
                                DeliverySegment.rvDeliveryDetail.setAdapter(DeliverySegment.adapter);
                            } else if (fromWhere == 2) {
                                userList.remove(getPosition());
                                UndeliverySegment.adapter = new DeliveryDetailAdapterofDeliveryBoy(context, userList, 2);
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
        }
    }
}