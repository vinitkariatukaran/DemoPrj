
package com.demoprj.BaseRequest.RestModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GasSizePrice {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gas_type_id")
    @Expose
    private String gasTypeId;
    @SerializedName("gas_size")
    @Expose
    private String gasSize;
    @SerializedName("price")
    @Expose
    private String price;

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

}
