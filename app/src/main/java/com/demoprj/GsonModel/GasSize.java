
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GasSize {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("gas_size")
    @Expose
    private String gasSize;
    @SerializedName("gas_type_id")
    @Expose
    private String gasTypeId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GasSize() {
    }

    /**
     * 
     * @param price
     * @param gasSize
     * @param gasTypeId
     */
    public GasSize(String price, String gasSize, String gasTypeId) {
        this.price = price;
        this.gasSize = gasSize;
        this.gasTypeId = gasTypeId;
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

}
