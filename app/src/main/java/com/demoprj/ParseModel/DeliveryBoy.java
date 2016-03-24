package com.demoprj.ParseModel;


import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mounil.shah on 11/5/2015.
 */
@ParseClassName("DeliveryBoy")
public class DeliveryBoy extends ParseObject {

    public String getName() {
        return getString(AppConstant.DELIVERYBOY_NAME);
    }

    public void setName(String name) {
        put(AppConstant.DELIVERYBOY_NAME, name);
    }

    public String getAddress() {
        return getString(AppConstant.DELIVERYBOY_ADDRESS);
    }

    public void setAddress(String address) {
        put(AppConstant.DELIVERYBOY_ADDRESS, address);
    }


    public int getPhoneNumber() {

        return getInt(AppConstant.DELIVERYBOY_PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        put(AppConstant.DELIVERYBOY_PHONE_NUMBER, phoneNumber);
    }

    public String getUserName() {
        return getString(AppConstant.DELIVERYBOY_USERNAME);
    }

    public void setUserName(String userName) {
        put(AppConstant.DELIVERYBOY_USERNAME, userName);
    }

    public String getPassword() {
        return getString(AppConstant.DELIVERYBOY_PASSWORD);
    }

    public void setPassword(String password) {
        put(AppConstant.DELIVERYBOY_PASSWORD, password);
    }

    public boolean getIsAdmin() {
        return getBoolean(AppConstant.DELIVERYBOY_IS_ADMIN);
    }

    public void setIsAdmin(String isAdmin) {
        put(AppConstant.DELIVERYBOY_IS_ADMIN, isAdmin);
    }
}
