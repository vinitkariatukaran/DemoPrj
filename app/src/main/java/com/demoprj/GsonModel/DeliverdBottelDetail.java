
package com.demoprj.GsonModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DeliverdBottelDetail {

    @SerializedName("1")
    @Expose
    private com.demoprj.GsonModel._1 _1;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeliverdBottelDetail() {
    }

    /**
     * 
     * @param _1
     */
    public DeliverdBottelDetail(com.demoprj.GsonModel._1 _1) {
        this._1 = _1;
    }

    /**
     * 
     * @return
     *     The _1
     */
    public com.demoprj.GsonModel._1 get1() {
        return _1;
    }

    /**
     * 
     * @param _1
     *     The 1
     */
    public void set1(com.demoprj.GsonModel._1 _1) {
        this._1 = _1;
    }

}
