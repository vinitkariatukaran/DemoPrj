package com.demoprj.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.demoprj.BaseRequest.RestModel.TransactionDetail;
//import com.demoprj.Constant.AppConstant;
//import com.demoprj.ParseModel.GasBooking;
//import com.demoprj.ParseModel.GasSize;
//import com.demoprj.ParseModel.GasType;
//import com.demoprj.ParseModel.User;
import com.demoprj.R;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mounil on 13/12/2015.
 */
public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder> {

//        ArrayList<GasBooking> userList = new ArrayList<>();
    List<TransactionDetail> transList = new ArrayList<>();
    Context context;

//    public TransactionDetailAdapter(Context context, ArrayList<GasBooking> userList) {
//        this.context = context;
//        this.userList = userList;
//    }

    public TransactionDetailAdapter(Context context, List<TransactionDetail> transactionDetailList) {
        this.context = context;
        this.transList = transactionDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.particular_delivery_detail, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//        final GasBooking gasBooking = userList.get(i);
        final TransactionDetail details = transList.get(i);
//        User u = (User) gasBooking.get(AppConstant.GASBOOKING_USER_ID);
        viewHolder.lblParticularDeliveryCustomerId.setText(details.getUserInfoId() + "");
        viewHolder.lblParticularDeliveryCustomerName.setText(details.getFullName());
        viewHolder.lblParticularDeliveryCustomerLandmark.setText(details.getLandmark());
        if (!details.getIsService().equals(1)) {
            viewHolder.lblParticularDeliveryGasType.setText(details.getGasType());
//            final GasType gasType = (GasType) u.get(AppConstant.USER_GAS_TYPE_ID);
//            ParseQuery<GasType> gasTypeQuery = ParseQuery.getQuery(GasType.class);
//            gasTypeQuery.whereEqualTo(AppConstant.OBJECT_ID, gasType.getObjectId());
//            gasTypeQuery.findInBackground(new FindCallback<GasType>() {
//                @Override
//                public void done(List<GasType> objects, ParseException e) {
//                    if (e == null) {
//                        if (objects.size() > 0) {
//                            viewHolder.lblParticularDeliveryGasType.setText(objects.get(0).getGasType());
//                        }
//                    } else {
//
//                    }
//                }
//            });
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm aa");
        if (!details.getDateOfDelivery().equals("0000-00-00 00:00:00")) {
            Date DeliveryDate = getDate(details.getDateOfDelivery());
            Date BookingDate = getDate(details.getDateOfBooking());
            Log.e("Booking & deliverDate", BookingDate + "    :::::    " + DeliveryDate);
            viewHolder.llParticularDeliveryDate.setVisibility(View.VISIBLE);
            viewHolder.lblParticularDeliveryCustomerBDate.setText(sdf.format(BookingDate));
            viewHolder.lblParticularDeliveryCustomerDDate.setText(sdf.format(DeliveryDate));
        } else {
            Date BookingDate1 = getDate(details.getDateOfBooking());
            Log.e("Booking ", BookingDate1 + "");
            viewHolder.lblParticularDeliveryCustomerBDate.setText(sdf.format(BookingDate1));
            viewHolder.lblParticularDeliveryCustomerDDate.setText("Pending");
        }

        if (details.getIsService().equals(1)) {
            viewHolder.llParticularDeliveryServiceCharge.setVisibility(View.VISIBLE);
            viewHolder.llParticularDeliveryServiceNote.setVisibility(View.VISIBLE);
            viewHolder.llParticularDeliverySizePrice.setVisibility(View.GONE);
            viewHolder.llParticularDeliveryChargeTotal.setVisibility(View.GONE);
            viewHolder.llParticularDeliveryTypeQuantity.setVisibility(View.GONE);
            viewHolder.lblParticularDeliveryServiceCharge.setText(details.getServiceCharge());
            viewHolder.lblParticularDeliveryServiceNote.setText((String) details.getServiceNote());
        } else {
            viewHolder.llParticularDeliveryServiceCharge.setVisibility(View.GONE);
            viewHolder.llParticularDeliveryServiceNote.setVisibility(View.GONE);
            viewHolder.llParticularDeliverySizePrice.setVisibility(View.VISIBLE);
            viewHolder.llParticularDeliveryChargeTotal.setVisibility(View.VISIBLE);
            viewHolder.llParticularDeliveryTypeQuantity.setVisibility(View.VISIBLE);
            double total = Double.parseDouble(details.getTotal());
            if (!details.getDeliveryCharge().equals("")) {

                double charge =  (double)Integer.parseInt(details.getDeliveryCharge());
                viewHolder.lblParticularDeliveryGasPrice.setText((total - charge)+"");
                viewHolder.lblParticularDeliveryCharge.setText(charge + "");
                viewHolder.lblParticularDeliveryTotal.setText(total + "");
            } else {
                viewHolder.lblParticularDeliveryGasPrice.setText(total + "");
                viewHolder.lblParticularDeliveryCharge.setText("0");
                viewHolder.lblParticularDeliveryTotal.setText(total + "");
            }
//            GasSize gasSize = (GasSize) gasBooking.get(AppConstant.GASBOOKING_GAS_SIZE_ID);
//            if (gasSize != null) {
                viewHolder.lblParticularDeliveryGasSize.setText(details.getGasSize() + "");
//            }
            viewHolder.lblParticularDeliveryGasQuantity.setText(details.getQuantity() + "");
        }
//        ParseUser db = (ParseUser) gasBooking.get(AppConstant.GASBOOKING_DELIVERYBOY_ID);
//        String DeliveryBoy = db.getUsername();
        viewHolder.lblParticularDeliveryBoy.setText("Deleivery boy 2");
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
        return transList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView lblParticularDeliveryCustomerId;
        TextView lblParticularDeliveryCustomerName;
        TextView lblParticularDeliveryCustomerLandmark;
        TextView lblParticularDeliveryGasType;
        TextView lblParticularDeliveryGasPrice;
        TextView lblParticularDeliveryGasSize;
        TextView lblParticularDeliveryGasQuantity;
        TextView lblParticularDeliveryServiceNote;
        TextView lblParticularDeliveryServiceCharge;
        TextView lblParticularDeliveryCustomerBDate;
        TextView lblParticularDeliveryCustomerDDate;
        TextView lblParticularDeliveryBoy;
        TextView lblParticularDeliveryCharge;
        TextView lblParticularDeliveryTotal;

        LinearLayout llParticularDeliveryDate;
        LinearLayout llParticularDeliveryTypeQuantity;
        LinearLayout llParticularDeliverySizePrice;
        LinearLayout llParticularDeliveryChargeTotal;
        LinearLayout llParticularDeliveryServiceNote;
        LinearLayout llParticularDeliveryServiceCharge;
        LinearLayout llParticularDeliveryLayout;

        Spinner spnParticularDeliveryBoy;
        Button btnEditDeliveryBoy;
        Button btnParticularDeliverySave;
        Button btnDeleteDeliveryDetail;


        public ViewHolder(View itemView) {
            super(itemView);
            llParticularDeliveryDate = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryDate);
            llParticularDeliveryTypeQuantity = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryTypeQuantity);
            llParticularDeliverySizePrice = (LinearLayout) itemView.findViewById(R.id.llParticularDeliverySizePrice);
            llParticularDeliveryServiceNote = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryServiceNote);
            llParticularDeliveryServiceCharge = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryServiceCharge);
            llParticularDeliveryLayout = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryLayout);
            llParticularDeliveryChargeTotal = (LinearLayout) itemView.findViewById(R.id.llParticularDeliveryChargeTotal);

            btnParticularDeliverySave = (Button) itemView.findViewById(R.id.btnParticularDeliverySave);
            btnEditDeliveryBoy = (Button) itemView.findViewById(R.id.btnEditDeliveryBoy);
            btnDeleteDeliveryDetail = (Button) itemView.findViewById(R.id.btnDeleteDeliveryDetail);

            lblParticularDeliveryTotal = (TextView) itemView.findViewById(R.id.lblParticularDeliveryTotal);
            lblParticularDeliveryCharge = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCharge);
            lblParticularDeliveryCustomerBDate = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCustomerBDate);
            lblParticularDeliveryCustomerDDate = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCustomerDDate);
            lblParticularDeliveryServiceCharge = (TextView) itemView.findViewById(R.id.lblParticularDeliveryServiceCharge);
            lblParticularDeliveryServiceNote = (TextView) itemView.findViewById(R.id.lblParticularDeliveryServiceNote);
            lblParticularDeliveryCustomerId = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCustomerId);
            lblParticularDeliveryCustomerName = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCustomerName);
            lblParticularDeliveryCustomerLandmark = (TextView) itemView.findViewById(R.id.lblParticularDeliveryCustomerLandmark);
            lblParticularDeliveryGasType = (TextView) itemView.findViewById(R.id.lblParticularDeliveryGasType);
            lblParticularDeliveryGasPrice = (TextView) itemView.findViewById(R.id.lblParticularDeliveryGasPrice);
            lblParticularDeliveryGasSize = (TextView) itemView.findViewById(R.id.lblParticularDeliveryGasSize);
            lblParticularDeliveryGasQuantity = (TextView) itemView.findViewById(R.id.lblParticularDeliveryGasQuantity);
            lblParticularDeliveryBoy = (TextView) itemView.findViewById(R.id.lblParticularDeliveryBoy);

            spnParticularDeliveryBoy = (Spinner) itemView.findViewById(R.id.spnParticularDeliveryBoy);

            btnEditDeliveryBoy.setVisibility(View.GONE);
            btnParticularDeliverySave.setVisibility(View.GONE);
            btnDeleteDeliveryDetail.setVisibility(View.GONE);

            spnParticularDeliveryBoy.setVisibility(View.GONE);
        }
    }
}