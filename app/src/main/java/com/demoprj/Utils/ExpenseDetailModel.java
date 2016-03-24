package com.demoprj.Utils;

/**
 * Created by Mounil on 2/20/16.
 */
public class ExpenseDetailModel {
    String GasSizeId;
    Double GasSize;
    int TotalQty;
    Double Amount;
    boolean Service;
    String serviceNote;
    public ExpenseDetailModel() {
    }

    public ExpenseDetailModel(String gasSizeId, Double gasSize, int totalQty, Double amount, boolean service) {
        GasSizeId = gasSizeId;
        GasSize = gasSize;
        TotalQty = totalQty;
        Amount = amount;
        this.serviceNote = serviceNote;
    }

    public boolean isService() {
        return Service;
    }

    public void setService(boolean service) {
        Service = service;
    }

    public String getServiceNote() {
        return serviceNote;
    }

    public void setServiceNote(String serviceNote) {
        this.serviceNote = serviceNote;
    }

    public String getGasSizeId() {
        return GasSizeId;
    }

    public void setGasSizeId(String gasSizeId) {
        GasSizeId = gasSizeId;
    }

    public Double getGasSize() {
        return GasSize;
    }

    public void setGasSize(Double gasSize) {
        GasSize = gasSize;
    }

    public int getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(int totalQty) {
        TotalQty = totalQty;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "ExpenseDetailModel{" +
                "Amount=" + Amount +
                ", TotalQty=" + TotalQty +
                ", GasSize=" + GasSize +
                ", GasSizeId='" + GasSizeId + '\'' +
                '}';
    }
}
