
package com.demoprj.BaseRequest.RestModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Login {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("emailVerified")
    @Expose
    private String emailVerified;
    @SerializedName("PhoneNumber")
    @Expose
    private Object PhoneNumber;
    @SerializedName("isAdmin")
    @Expose
    private String isAdmin;

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
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public Object getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public Object getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The emailVerified
     */
    public String getEmailVerified() {
        return emailVerified;
    }

    /**
     * 
     * @param emailVerified
     *     The emailVerified
     */
    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * 
     * @return
     *     The PhoneNumber
     */
    public Object getPhoneNumber() {
        return PhoneNumber;
    }

    /**
     * 
     * @param PhoneNumber
     *     The PhoneNumber
     */
    public void setPhoneNumber(Object PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    /**
     * 
     * @return
     *     The isAdmin
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * 
     * @param isAdmin
     *     The isAdmin
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

}
