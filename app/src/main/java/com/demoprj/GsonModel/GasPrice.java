
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GasPrice {

    @SerializedName("gas_type")
    @Expose
    private String gasType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GasPrice() {
    }

    /**
     * 
     * @param gasType
     */
    public GasPrice(String gasType) {
        this.gasType = gasType;
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

}
