package com.demoprj.ParseModel;

import com.demoprj.Constant.AppConstant;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by mounil.shah on 11/5/2015.
 */
@ParseClassName("UserInfo")
public class User extends ParseObject implements Serializable {

    public String getGasTypeId() {
        return getString(AppConstant.USER_GAS_TYPE_ID);
    }

    public void setGasTypeId(String gasTypeId) {
        put(AppConstant.USER_GAS_TYPE_ID, ParseObject.createWithoutData("GasType", gasTypeId));
    }

    public String getFirstName() {
        return getString(AppConstant.USER_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        put(AppConstant.USER_FIRST_NAME, firstName);
    }

    public String getMiddleName() {
        return getString(AppConstant.USER_MIDDLE_NAME);
    }

    public void setMiddleName(String middleName) {
        put(AppConstant.USER_MIDDLE_NAME, middleName);
    }

    public String getLastName() {
        return getString(AppConstant.USER_LAST_NAME);
    }

    public void setLastName(String lastName) {
        put(AppConstant.USER_LAST_NAME, lastName);
    }

    public String getLandmark() {
        return getString(AppConstant.USER_LANDMARK);
    }

    public void setLandmark(String landmark) {
        put(AppConstant.USER_LANDMARK, landmark);
    }

    public String getLatLng() {

        return getString(AppConstant.USER_LATLNG);
    }

    public void setLatLng(String latLng) {
        put(AppConstant.USER_LATLNG, latLng);
    }

    public String getAddress() {
        return getString(AppConstant.USER_ADDRESS);
    }

    public void setAddress(String address) {
        put(AppConstant.USER_ADDRESS, address);
    }

    public Long getphoneNumber() {

        return getLong(AppConstant.USER_PHONE_NUMBER);
    }

    public void setphoneNumber(long phoneNumber) {
        put(AppConstant.USER_PHONE_NUMBER, phoneNumber);
    }

    public int getCustomerId() {

        return getInt(AppConstant.USER_CUSTOMER_ID);
    }

    public void setCustomerId(int customerId) {
        put(AppConstant.USER_CUSTOMER_ID, customerId);
    }
    public Long getSecondaryMobileNumber() {

        return getLong(AppConstant.USER_SECONDARY_PHONE_NUMBER);
    }

    public void setSecondaryMobileNumber(long secondaryMobileNumber) {
        put(AppConstant.USER_SECONDARY_PHONE_NUMBER, secondaryMobileNumber);
    }

    public void setEmailId(String emailId) {
        put(AppConstant.USER_EMAILID, emailId);
    }

    public String getEmailId() {
        return getString(AppConstant.USER_EMAILID);
    }

    public void setDOB(String dob) {
        put(AppConstant.USER_DOB, dob);
    }

    public String getDOB() {
        return getString(AppConstant.USER_DOB);
    }
    public int getGasSize() {

        return getInt(AppConstant.USER_GAS_SIZE_ID);
    }

    public void setGasSizeId(int gasSizeId) {
        put(AppConstant.USER_GAS_SIZE_ID, gasSizeId);
    }
    public int getDeliveryCharge() {

        return getInt(AppConstant.USER_DELIVERY_CHARGE);
    }

    public void setDeliveryCharge(int deliveryCharge) {
        put(AppConstant.USER_DELIVERY_CHARGE, deliveryCharge);
    }
}
