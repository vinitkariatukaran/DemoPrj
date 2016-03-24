
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Customer {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("landmark")
    @Expose
    private String landmark;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Customer() {
    }

    /**
     * 
     * @param landmark
     * @param phoneNumber
     * @param address
     */
    public Customer(String address, String phoneNumber, String landmark) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.landmark = landmark;
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

}
