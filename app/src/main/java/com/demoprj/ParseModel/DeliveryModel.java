package com.demoprj.ParseModel;

/**
 * Created by Mounil on 26/12/2015.
 */
public class DeliveryModel {
    String ObjectId;
    String Name;
    int CustomerId;
    String Landmark;
    String Type;
    int Quantity;
    double Size;
    double Price;
    String DeliveryBoy;
    String ServiceCharge;
    String ServiceNote;
    boolean isService;


    public DeliveryModel() {
    }

    public DeliveryModel(String objectId,String name, int customerId, String landmark, String type, int quantity, int size, long price, String deliveryBoy, String serviceCharge, String serviceNote, boolean isService) {
        ObjectId = objectId;
        Name = name;
        CustomerId = customerId;
        Landmark = landmark;
        Type = type;
        Quantity = quantity;
        Size = size;
        Price = price;
        DeliveryBoy = deliveryBoy;
        ServiceCharge = serviceCharge;
        ServiceNote = serviceNote;
        this.isService = isService;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public void setObjectId(String objectId) {
        ObjectId = objectId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getSize() {
        return Size;
    }

    public void setSize(double size) {
        Size = size;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getDeliveryBoy() {
        return DeliveryBoy;
    }

    public void setDeliveryBoy(String deliveryBoy) {
        DeliveryBoy = deliveryBoy;
    }

    public String getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public String getServiceNote() {
        return ServiceNote;
    }

    public void setServiceNote(String serviceNote) {
        ServiceNote = serviceNote;
    }

    public boolean isService() {
        return isService;
    }

    public void setIsService(boolean isService) {
        this.isService = isService;
    }

    @Override
    public String toString() {
        return "DeliveryModel{" +
                "ObjectId='" + ObjectId + '\'' +
                ", Name='" + Name + '\'' +
                ", CustomerId=" + CustomerId +
                ", Landmark='" + Landmark + '\'' +
                ", Type='" + Type + '\'' +
                ", Quantity=" + Quantity +
                ", Size=" + Size +
                ", Price=" + Price +
                ", DeliveryBoy='" + DeliveryBoy + '\'' +
                ", ServiceCharge='" + ServiceCharge + '\'' +
                ", ServiceNote='" + ServiceNote + '\'' +
                ", isService=" + isService +
                '}';
    }
}
