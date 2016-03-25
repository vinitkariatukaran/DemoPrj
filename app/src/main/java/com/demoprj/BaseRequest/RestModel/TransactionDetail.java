
package com.demoprj.BaseRequest.RestModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class TransactionDetail implements Serializable{

    @SerializedName("gas_booking_id")
    @Expose
    private String gasBookingId;
    @SerializedName("date_of_booking")
    @Expose
    private String dateOfBooking;
    @SerializedName("date_of_delivery")
    @Expose
    private String dateOfDelivery;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("is_service")
    @Expose
    private String isService;
    @SerializedName("user_info_id")
    @Expose
    private String userInfoId;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("service_note")
    @Expose
    private Object serviceNote;
    @SerializedName("gas_size_id")
    @Expose
    private String gasSizeId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("gas_size")
    @Expose
    private String gasSize;
    @SerializedName("gas_type_id")
    @Expose
    private String gasTypeId;
    @SerializedName("gas_type")
    @Expose
    private String gasType;
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
     * 
     * @return
     *     The gasBookingId
     */
    public String getGasBookingId() {
        return gasBookingId;
    }

    /**
     * 
     * @param gasBookingId
     *     The gas_booking_id
     */
    public void setGasBookingId(String gasBookingId) {
        this.gasBookingId = gasBookingId;
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
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The deliveryCharge
     */
    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    /**
     * 
     * @param deliveryCharge
     *     The delivery_charge
     */
    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
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
     *     The serviceNote
     */
    public Object getServiceNote() {
        return serviceNote;
    }

    /**
     * 
     * @param serviceNote
     *     The service_note
     */
    public void setServiceNote(Object serviceNote) {
        this.serviceNote = serviceNote;
    }

    /**
     * 
     * @return
     *     The gasSizeId
     */
    public String getGasSizeId() {
        return gasSizeId;
    }

    /**
     * 
     * @param gasSizeId
     *     The gas_size_id
     */
    public void setGasSizeId(String gasSizeId) {
        this.gasSizeId = gasSizeId;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The gasSize
     */
    public String getGasSize() {
        return gasSize;
    }

    /**
     * 
     * @param gasSize
     *     The gas_size
     */
    public void setGasSize(String gasSize) {
        this.gasSize = gasSize;
    }

    /**
     * 
     * @return
     *     The gasTypeId
     */
    public String getGasTypeId() {
        return gasTypeId;
    }

    /**
     * 
     * @param gasTypeId
     *     The gas_type_id
     */
    public void setGasTypeId(String gasTypeId) {
        this.gasTypeId = gasTypeId;
    }

    /**
     * 
     * @return
     *     The gasType
     */
    public String getGasType() {
        return gasType;
    }

    /**
     * 
     * @param gasType
     *     The gas_type
     */
    public void setGasType(String gasType) {
        this.gasType = gasType;
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
