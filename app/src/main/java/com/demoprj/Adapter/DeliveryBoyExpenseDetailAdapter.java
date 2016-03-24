package com.demoprj.Adapter;

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
import com.demoprj.Fragment.DeliveryDetailsFragment;
import com.demoprj.ParseModel.DeliveryModel;
import com.demoprj.ParseModel.GasBooking;
import com.demoprj.ParseModel.GasSize;
import com.demoprj.ParseModel.GasType;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularDeliveryDetail;
import com.demoprj.R;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.demoprj.Utils.ExpenseDetailModel;
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
public class DeliveryBoyExpenseDetailAdapter extends RecyclerView.Adapter<DeliveryBoyExpenseDetailAdapter.ViewHolder> {

    ArrayList<ExpenseDetailModel> expenseList = new ArrayList<>();
    Context context;
    public DeliveryBoyExpenseDetailAdapter(Context context, ArrayList<ExpenseDetailModel> expenseList){
        this.context = context;
        this.expenseList = expenseList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_deliveryboy_detail, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if(expenseList.get(i).isService()){
            viewHolder.lblDBSize.setText(expenseList.get(i).getGasSizeId() + "");
            viewHolder.lblDBQty.setText(expenseList.get(i).getTotalQty() + "");
            viewHolder.lblDBAmount.setText(expenseList.get(i).getAmount() + "");
        }else {
            viewHolder.lblDBSize.setText(expenseList.get(i).getGasSize() + "");
            viewHolder.lblDBQty.setText(expenseList.get(i).getTotalQty() + "");
            viewHolder.lblDBAmount.setText(expenseList.get(i).getAmount() + "");
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblDBSize;
        public TextView lblDBQty;
        public TextView lblDBAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            lblDBSize = (TextView)itemView.findViewById(R.id.lblDBSize);
            lblDBQty = (TextView)itemView.findViewById(R.id.lblDBQty);
            lblDBAmount = (TextView)itemView.findViewById(R.id.lblDBAmount);
        }
    }
}