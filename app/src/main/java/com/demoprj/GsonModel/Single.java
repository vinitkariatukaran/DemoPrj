
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Single {

    @SerializedName("user_info_id")
    @Expose
    private String userInfoId;
    @SerializedName("date_of_booking")
    @Expose
    private String dateOfBooking;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("gas_size_id")
    @Expose
    private String gasSizeId;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("service_note")
    @Expose
    private String serviceNote;
    @SerializedName("is_service")
    @Expose
    private String isService;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Single() {
    }

    /**
     * 
     * @param total
     * @param dateOfBooking
     * @param isService
     * @param userInfoId
     * @param userId
     * @param serviceNote
     * @param quantity
     * @param serviceCharge
     * @param deliveryCharge
     * @param gasSizeId
     */
    public Single(String userInfoId, String dateOfBooking, String userId, String gasSizeId, String deliveryCharge, String quantity, String total, String serviceCharge, String serviceNote, String isService) {
        this.userInfoId = userInfoId;
        this.dateOfBooking = dateOfBooking;
        this.userId = userId;
        this.gasSizeId = gasSizeId;
        this.deliveryCharge = deliveryCharge;
        this.quantity = quantity;
        this.total = total;
        this.serviceCharge = serviceCharge;
        this.serviceNote = serviceNote;
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
    public String getServiceNote() {
        return serviceNote;
    }

    /**
     * 
     * @param serviceNote
     *     The service_note
     */
    public void setServiceNote(String serviceNote) {
        this.serviceNote = serviceNote;
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

}
