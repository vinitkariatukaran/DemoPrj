package com.demoprj.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.DeliveryModel;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mounil on 13/12/2015.
 */
public class SearchDeliveryDetailAdapter extends RecyclerView.Adapter<SearchDeliveryDetailAdapter.ViewHolder> {

    ArrayList<DeliveryModel> userList = new ArrayList<>();
    Context context;

    public SearchDeliveryDetailAdapter(Context context, ArrayList<DeliveryModel> userList){
        this.context = context;
        this.userList = userList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_delivery_detail_info, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.lblDeliveryCustomerId.setText(userList.get(i).getCustomerId() + "");
        viewHolder.lblDeliveryCustomerName.setText(userList.get(i).getName());
        viewHolder.lblDeliveryCustomerLandmark.setText(userList.get(i).getLandmark());
        if(userList.get(i).isService()){
            viewHolder.llDeliveryServiceCharge.setVisibility(View.VISIBLE);
            viewHolder.llDeliveryServiceNote.setVisibility(View.VISIBLE);
            viewHolder.ivService.setVisibility(View.VISIBLE);
            viewHolder.llDeliverySizePrice.setVisibility(View.GONE);
            viewHolder.llDeliveryTypeQuantity.setVisibility(View.GONE);
            viewHolder.lblDeliveryServiceCharge.setText(userList.get(i).getServiceCharge());
            viewHolder.lblDeliveryServiceNote.setText(userList.get(i).getServiceNote());
        }else{
            viewHolder.llDeliveryServiceCharge.setVisibility(View.GONE);
            viewHolder.llDeliveryServiceNote.setVisibility(View.GONE);
            viewHolder.ivService.setVisibility(View.GONE);
            viewHolder.llDeliverySizePrice.setVisibility(View.VISIBLE);
            viewHolder.llDeliveryTypeQuantity.setVisibility(View.VISIBLE);
            viewHolder.lblDeliveryGasType.setText(userList.get(i).getType());
            viewHolder.lblDeliveryGasPrice.setText(userList.get(i).getPrice() + "");
            viewHolder.lblDeliveryGasSize.setText(userList.get(i).getSize()+"");
//            viewHolder.lblDeliveryGasSize.setText(gasSize.getGasSize()+"");
            viewHolder.lblDeliveryGasQuantity.setText(userList.get(i).getQuantity()+"");
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblDeliveryCustomerId;
        public TextView lblDeliveryCustomerName;
        public TextView lblDeliveryCustomerLandmark;
        public TextView lblDeliveryGasType;
        public TextView lblDeliveryGasPrice;
        public TextView lblDeliveryGasSize;
        public TextView lblDeliveryGasQuantity;
        public TextView lblDeliveryServiceNote;
        public TextView lblDeliveryServiceCharge;

        public LinearLayout llDeliveryTypeQuantity;
        public LinearLayout llDeliverySizePrice;
        public LinearLayout llDeliveryServiceNote;
        public LinearLayout llDeliveryServiceCharge;
        public ImageView ivService;


        public ViewHolder(View itemView) {
            super(itemView);
            llDeliveryTypeQuantity = (LinearLayout) itemView.findViewById(R.id.llDeliveryTypeQuantity);
            llDeliverySizePrice = (LinearLayout) itemView.findViewById(R.id.llDeliverySizePrice);
            llDeliveryServiceNote = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceNote);
            llDeliveryServiceCharge = (LinearLayout) itemView.findViewById(R.id.llDeliveryServiceCharge);

            ivService = (ImageView) itemView.findViewById(R.id.ivService);

            lblDeliveryServiceCharge = (TextView)itemView.findViewById(R.id.lblDeliveryServiceCharge);
            lblDeliveryServiceNote = (TextView)itemView.findViewById(R.id.lblDeliveryServiceNote);
            lblDeliveryCustomerId = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerId);
            lblDeliveryCustomerName = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerName);
            lblDeliveryCustomerLandmark = (TextView)itemView.findViewById(R.id.lblDeliveryCustomerLandmark);
            lblDeliveryGasType = (TextView)itemView.findViewById(R.id.lblDeliveryGasType);
            lblDeliveryGasPrice = (TextView)itemView.findViewById(R.id.lblDeliveryGasPrice);
            lblDeliveryGasSize = (TextView)itemView.findViewById(R.id.lblDeliveryGasSize);
            lblDeliveryGasQuantity = (TextView)itemView.findViewById(R.id.lblDeliveryGasQuantity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent next = new Intent(context, ParticularCustomerDetailInfo.class);
            next.putExtra("ObjectId", userList.get(getPosition()).getObjectId());
//            context.startActivity(next);
        }
    }
}
