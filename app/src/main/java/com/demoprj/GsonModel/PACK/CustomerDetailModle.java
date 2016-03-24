
package com.demoprj.GsonModel.PACK;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class CustomerDetailModle implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("lat_lng")
    @Expose
    private String latLng;
    @SerializedName("gas_type_id")
    @Expose
    private String gasTypeId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("second_mobile_number")
    @Expose
    private String secondMobileNumber;
    @SerializedName("gas_size_id")
    @Expose
    private String gasSizeId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("gas_size")
    @Expose
    private GasSize gasSize;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerDetailModle() {
    }

    /**
     * 
     * @param middleName
     * @param lastName
     * @param latLng
     * @param gasSize
     * @param gasTypeId
     * @param deliveryCharge
     * @param gasSizeId
     * @param amount
     * @param id
     * @param secondMobileNumber
     * @param landmark
     * @param customerId
     * @param phoneNumber
     * @param address
     * @param email
     * @param userId
     * @param dob
     * @param fullName
     * @param firstName
     */
    public CustomerDetailModle(Integer id, String firstName, String middleName, String lastName, String phoneNumber, String latLng, String gasTypeId, String address, String landmark, String email, String dob, String secondMobileNumber, String gasSizeId, String amount, String deliveryCharge, String customerId, String userId, String fullName, GasSize gasSize) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.latLng = latLng;
        this.gasTypeId = gasTypeId;
        this.address = address;
        this.landmark = landmark;
        this.email = email;
        this.dob = dob;
        this.secondMobileNumber = secondMobileNumber;
        this.gasSizeId = gasSizeId;
        this.amount = amount;
        this.deliveryCharge = deliveryCharge;
        this.customerId = customerId;
        this.userId = userId;
        this.fullName = fullName;
        this.gasSize = gasSize;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * 
     * @param middleName
     *     The middle_name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     *     The latLng
     */
    public String getLatLng() {
        return latLng;
    }

    /**
     * 
     * @param latLng
     *     The lat_lng
     */
    public void setLatLng(String latLng) {
        this.latLng = latLng;
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
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * 
     * @param dob
     *     The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * 
     * @return
     *     The secondMobileNumber
     */
    public String getSecondMobileNumber() {
        return secondMobileNumber;
    }

    /**
     * 
     * @param secondMobileNumber
     *     The second_mobile_number
     */
    public void setSecondMobileNumber(String secondMobileNumber) {
        this.secondMobileNumber = secondMobileNumber;
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
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
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
     *     The customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId
     *     The customer_id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    /**
     * 
     * @return
     *     The gasSize
     */
    public GasSize getGasSize() {
        return gasSize;
    }

    /**
     * 
     * @param gasSize
     *     The gas_size
     */
    public void setGasSize(GasSize gasSize) {
        this.gasSize = gasSize;
    }

}
