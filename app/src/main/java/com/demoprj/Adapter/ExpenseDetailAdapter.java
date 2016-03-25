package com.demoprj.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demoprj.BaseRequest.RestModel.ExpenseDetail;
import com.demoprj.Constant.AppConstant;
import com.demoprj.DeliveryBoyApp.CustomerDetailInfoActivityDeliveryBoy;
import com.demoprj.DeliveryBoyWiseDetailActivity;
import com.demoprj.ParseModel.Expense;
import com.demoprj.ParseModel.User;
import com.demoprj.ParticularCustomerDetailInfo;
import com.demoprj.R;
import com.demoprj.Utils.Utils;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Mounil on 13/12/2015.
 */
public class ExpenseDetailAdapter extends RecyclerView.Adapter<ExpenseDetailAdapter.ViewHolder> {

//    ArrayList<Expense> expenseList = new ArrayList<>();
    ArrayList<ExpenseDetail> expenseDetailList = new ArrayList<>();
    Context context;
    String date;
//    public ExpenseDetailAdapter(Context context, ArrayList<Expense> expenseList,String date){
//        this.context = context;
//        this.expenseList = expenseList;
//        this.date = date;
//    }

    public ExpenseDetailAdapter(Context context, ArrayList<ExpenseDetail> expenseDetailList, String date) {
        this.context = context;
        this.expenseDetailList = expenseDetailList;
        this.date = date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_for_display_expense_detail_admin, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        ParseUser user = (ParseUser) expenseList.get(i).get(AppConstant.EXPENSE_DELIVERYBOY);
        ExpenseDetail expenseDetail = expenseDetailList.get(i);
        viewHolder.lblExpenseDeliveryBoy.setText(expenseDetail.getName());
        viewHolder.lblExpenseDeliveryExpense.setText(expenseDetail.getAmount()+"");
        viewHolder.lblExpenseDeliveryTotal.setText(expenseDetail.getDeliveryTotal() + "");
        Double total = Double.parseDouble(expenseDetail.getDeliveryTotal()) - Double.parseDouble(expenseDetail.getAmount());
        viewHolder.lblExpenseDeliveryAmount.setText(total+"");
    }

    @Override
    public int getItemCount() {
        return expenseDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblExpenseDeliveryBoy;
        public TextView lblExpenseDeliveryExpense;
        public TextView lblExpenseDeliveryTotal;
        public TextView lblExpenseDeliveryAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            lblExpenseDeliveryBoy = (TextView)itemView.findViewById(R.id.lblExpenseDeliveryBoy);
            lblExpenseDeliveryExpense = (TextView)itemView.findViewById(R.id.lblExpenseDeliveryExpense);
            lblExpenseDeliveryTotal = (TextView)itemView.findViewById(R.id.lblExpenseDeliveryTotal);
            lblExpenseDeliveryAmount = (TextView)itemView.findViewById(R.id.lblExpenseDeliveryAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(Utils.isInternetAvailable(context)){
//                ParseUser user = (ParseUser) expenseList.get(getPosition()).get(AppConstant.EXPENSE_DELIVERYBOY);
                ExpenseDetail detail = expenseDetailList.get(getPosition());
                Intent next = new Intent(context, DeliveryBoyWiseDetailActivity.class);
                next.putExtra("Date",date);
                next.putExtra("ObjectId",detail.getUserId());
                context.startActivity(next);
            }else {
                Toast.makeText(context, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }

        }
    }
}
