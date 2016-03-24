package com.demoprj.ParseModel;


import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mounil.shah on 11/5/2015.
 */
@ParseClassName("GasBooking")
public class GasBooking extends ParseObject implements Serializable {

    public String getUserId() {
        return getString(AppConstant.GASBOOKING_USER_ID);
    }

    public void setUserId(String userId) {
        put(AppConstant.GASBOOKING_USER_ID, ParseObject.createWithoutData("UserInfo", userId));
    }

    public boolean getDeliverySatus() {
        return getBoolean(AppConstant.GASBOOKING_DELIVERY_STATUS);
    }

    public void setDeliverySatus(boolean deliveryStatus) {
        put(AppConstant.GASBOOKING_DELIVERY_STATUS, deliveryStatus);
    }

    public Date getDateOfBooking() {
        return getDate(AppConstant.GASBOOKING_DATE_OF_BOOKING);
    }

    public void setDateOfBooking(Date dateOfBooking) {
        put(AppConstant.GASBOOKING_DATE_OF_BOOKING, dateOfBooking);
    }

    public Date getDateOfDelivery() {
        return getDate(AppConstant.GASBOOKING_DATE_OF_DELIVERY);
    }

    public void setDateOfDelivery(Date dateOfDelivery) {
        put(AppConstant.GASBOOKING_DATE_OF_DELIVERY, dateOfDelivery);
    }

    public String getDeliveryBoyId() {
        return getString(AppConstant.GASBOOKING_DELIVERYBOY_ID);
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        put(AppConstant.GASBOOKING_DELIVERYBOY_ID, ParseObject.createWithoutData("_User", deliveryBoyId));
    }

    public Double getTotal() {
        return getDouble(AppConstant.GASBOOKING_TOTAL);
    }

    public void setTotal(Double total) {
        put(AppConstant.GASBOOKING_TOTAL, total);
    }

    public int getQuantity() {
        return getInt(AppConstant.GASBOOKING_QUANTITY);
    }

    public void setQuantity(int quantity) {
        put(AppConstant.GASBOOKING_QUANTITY, quantity);
    }

    public String getName() {
        return getString(AppConstant.GASBOOKING_NAME);
    }

    public void setName(String name) {
            put(AppConstant.GASBOOKING_NAME, name);
    }

    public String getGasSizeId() {
        return getString(AppConstant.GASBOOKING_GAS_SIZE_ID);
    }

    public void setGasSizeId(String gasSizeId) {
        put(AppConstant.GASBOOKING_GAS_SIZE_ID, ParseObject.createWithoutData("GasSize",gasSizeId));
    }

    public boolean getisService() {
        return getBoolean(AppConstant.GASBOOKING_IS_SERVICE);
    }

    public void setisService(boolean isService) {
        put(AppConstant.GASBOOKING_IS_SERVICE, isService);
    }

    public String getServiceCharge() {
        return getString(AppConstant.GASBOOKING_SERVICE_CHARGE);
    }

    public void setServiceCharge(String serviceCharge) {
        put(AppConstant.GASBOOKING_SERVICE_CHARGE, serviceCharge);
    }

    public String getServiceNote() {
        return getString(AppConstant.GASBOOKING_SERVICE_NOTE);
    }

    public void setServiceNote(String serviceNote) {
        put(AppConstant.GASBOOKING_SERVICE_NOTE, serviceNote);
    }
    public String getDeliveryCharge() {
        return getString(AppConstant.GASBOOKING_DELIVERY_CHARGE);
    }

    public void setDeliveryCharge(String deliveryCharge) {
        put(AppConstant.GASBOOKING_DELIVERY_CHARGE, deliveryCharge);
    }

}
