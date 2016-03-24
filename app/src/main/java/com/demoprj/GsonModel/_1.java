
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class _1 {

    @SerializedName("single")
    @Expose
    private Single single;
    @SerializedName("gas_size")
    @Expose
    private GasSize gasSize;
    @SerializedName("gas_price")
    @Expose
    private GasPrice gasPrice;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("name")
    @Expose
    private Name name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _1() {
    }

    /**
     * 
     * @param single
     * @param name
     * @param gasPrice
     * @param gasSize
     * @param customer
     */
    public _1(Single single, GasSize gasSize, GasPrice gasPrice, Customer customer, Name name) {
        this.single = single;
        this.gasSize = gasSize;
        this.gasPrice = gasPrice;
        this.customer = customer;
        this.name = name;
    }

    /**
     * 
     * @return
     *     The single
     */
    public Single getSingle() {
        return single;
    }

    /**
     * 
     * @param single
     *     The single
     */
    public void setSingle(Single single) {
        this.single = single;
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

    /**
     * 
     * @return
     *     The gasPrice
     */
    public GasPrice getGasPrice() {
        return gasPrice;
    }

    /**
     * 
     * @param gasPrice
     *     The gas_price
     */
    public void setGasPrice(GasPrice gasPrice) {
        this.gasPrice = gasPrice;
    }

    /**
     * 
     * @return
     *     The customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * 
     * @param customer
     *     The customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * 
     * @return
     *     The name
     */
    public Name getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(Name name) {
        this.name = name;
    }

}
