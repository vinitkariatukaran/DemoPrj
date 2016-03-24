
package com.demoprj.GsonModel.PACK;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class GasSize  implements Serializable {

    @SerializedName("gas_size")
    @Expose
    private String gasSize;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GasSize() {
    }

    /**
     * 
     * @param gasSize
     */
    public GasSize(String gasSize) {
        this.gasSize = gasSize;
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

}
