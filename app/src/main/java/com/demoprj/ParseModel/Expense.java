package com.demoprj.ParseModel;

import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Mounil on 1/30/16.
 */
@ParseClassName("Expense")
public class Expense extends ParseObject {

    public Date getDate() {
        return getDate(AppConstant.EXPENSE_DATE);
    }

    public void setDate(Date date) {
        put(AppConstant.EXPENSE_DATE, date);
    }

    public String getDeliveryBoyId() {
        return getString(AppConstant.EXPENSE_DELIVERYBOY);
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        put(AppConstant.EXPENSE_DELIVERYBOY, ParseObject.createWithoutData("_User", deliveryBoyId));
    }

    public Long getAmount() {
        return getLong(AppConstant.EXPENSE_CHARGE);
    }

    public void setAmount(long amount) {
        put(AppConstant.EXPENSE_CHARGE, amount);
    }
    public Double getDeliveryAmount() {
        return getDouble(AppConstant.EXPENSE_DELIVERYAMOUNT);
    }

    public void setDeliveryAmount(Double deliveryAmount) {
        put(AppConstant.EXPENSE_DELIVERYAMOUNT, deliveryAmount);
    }

    public String getDetail() {
        return getString(AppConstant.EXPENSE_DETAIL);
    }

    public void setDetail(String detail) {
        put(AppConstant.EXPENSE_DETAIL, detail);
    }

}
