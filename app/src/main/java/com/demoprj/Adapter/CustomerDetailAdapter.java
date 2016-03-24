package com.demoprj.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demoprj.Constant.AppConstant;
import com.demoprj.DeliveryBoyApp.CustomerDetailInfoActivityDeliveryBoy;
import com.demoprj.GsonModel.CustomerDetail;
import com.demoprj.GsonModel.PACK.CustomerDetailModle;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Mounil on 13/12/2015.
 */
public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.ViewHolder> {

    ArrayList<CustomerDetailModle> userList = new ArrayList<>();
    Context context;

    public CustomerDetailAdapter(Context context,ArrayList<CustomerDetailModle> userList){
        this.context = context;
        this.userList = userList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_cutomer_detail_info, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.lblCustomerId.setText(userList.get(i).getCustomerId() + "");
        viewHolder.lblCustomerName.setText(userList.get(i).getFullName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblCustomerId;
        public TextView lblCustomerName;

        public ViewHolder(View itemView) {
            super(itemView);
            lblCustomerId = (TextView)itemView.findViewById(R.id.lblCustomerId);
            lblCustomerName = (TextView)itemView.findViewById(R.id.lblCustomerName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(Utils.isInternetAvailable(context)) {
                Intent next = new Intent();
                ParseUser user = ParseUser.getCurrentUser();
                if(user.getBoolean(AppConstant.IS_ADMIN)) {
                    next = new Intent(context, ParticularCustomerDetailInfo.class);
                }else{
                    next = new Intent(context, CustomerDetailInfoActivityDeliveryBoy.class);
                }
                next.putExtra("CustomerId", userList.get(getPosition()).getCustomerId());
                next.putExtra("ObjectId", userList.get(getPosition()).getCustomerId());
                next.putExtra("CustomerDetail", userList.get(getPosition()));
                context.startActivity(next);

            }else {
                Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }
        }
    }
}
