package com.demoprj.ParseModel;

import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mounil.shah on 11/5/2015.
 */
@ParseClassName("GasSize")
public class GasSize extends ParseObject {

    public int getGasTypeId() {
        return getInt(AppConstant.USER_GAS_TYPE_ID);
    }

    public void setGasTypeId(int gasTypeId) {
        put(AppConstant.USER_GAS_TYPE_ID, gasTypeId);
    }

    public double getPrice() {
        return getInt(AppConstant.GAS_PRICE);
    }

    public void setPrice(double price) {
        put(AppConstant.GAS_PRICE, price);
    }

    public Double getGasSize() {
        return getDouble(AppConstant.GAS_SIZE);
    }

    public void setGasSize(long gasSize) {
        put(AppConstant.GAS_SIZE, gasSize);
    }
}
