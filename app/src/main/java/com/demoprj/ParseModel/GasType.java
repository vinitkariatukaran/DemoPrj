package com.demoprj.ParseModel;


import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mounil.shah on 11/5/2015.
 */
@ParseClassName("GasType")
public class GasType extends ParseObject {

    public String getGasType() {
        return getString(AppConstant.GAS_TYPE);
    }

    public void setGasType(String gasType) {
        put(AppConstant.GAS_TYPE, gasType);
    }
}
