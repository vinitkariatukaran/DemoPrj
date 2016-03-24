
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CustomerLastTranscDetail {

    @SerializedName("user_info_id")
    @Expose
    private String userInfoId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_of_booking")
    @Expose
    private String dateOfBooking;
    @SerializedName("date_of_delivery")
    @Expose
    private String dateOfDelivery;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("is_service")
    @Expose
    private String isService;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerLastTranscDetail() {
    }

    /**
     * 
     * @param landmark
     * @param dateOfBooking
     * @param phoneNumber
     * @param isService
     * @param dateOfDelivery
     * @param address
     * @param userInfoId
     * @param name
     * @param fullName
     * @param serviceCharge
     */
    public CustomerLastTranscDetail(String userInfoId, String name, String dateOfBooking, String dateOfDelivery, String serviceCharge, String isService, String address, String phoneNumber, String landmark, String fullName) {
        this.userInfoId = userInfoId;
        this.name = name;
        this.dateOfBooking = dateOfBooking;
        this.dateOfDelivery = dateOfDelivery;
        this.serviceCharge = serviceCharge;
        this.isService = isService;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.landmark = landmark;
        this.fullName = fullName;
    }

    /**
     * 
     * @return
     *     The userInfoId
     */
    public String getUserInfoId() {
        return userInfoId;
    }

    /**
     * 
     * @param userInfoId
     *     The user_info_id
     */
    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The dateOfBooking
     */
    public String getDateOfBooking() {
        return dateOfBooking;
    }

    /**
     * 
     * @param dateOfBooking
     *     The date_of_booking
     */
    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    /**
     * 
     * @return
     *     The dateOfDelivery
     */
    public String getDateOfDelivery() {
        return dateOfDelivery;
    }

    /**
     * 
     * @param dateOfDelivery
     *     The date_of_delivery
     */
    public void setDateOfDelivery(String dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    /**
     * 
     * @return
     *     The serviceCharge
     */
    public String getServiceCharge() {
        return serviceCharge;
    }

    /**
     * 
     * @param serviceCharge
     *     The service_charge
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * 
     * @return
     *     The isService
     */
    public String getIsService() {
        return isService;
    }

    /**
     * 
     * @param isService
     *     The is_service
     */
    public void setIsService(String isService) {
        this.isService = isService;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 
     * @param phoneNumber
     *     The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     * @return
     *     The landmark
     */
    public String getLandmark() {
        return landmark;
    }

    /**
     * 
     * @param landmark
     *     The landmark
     */
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
