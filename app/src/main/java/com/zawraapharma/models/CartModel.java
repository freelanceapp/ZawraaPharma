package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class CartModel implements Serializable {
    private String user_id;
    private String client_id;
    private String total;
    private String discount;
    private String total_after_discount;
    private String notes="";
    private String latitude;
    private String longitude;
    private List<BillData> bills;

    public CartModel(String user_id, String client_id, String total, String discount, String total_after_discount, String notes, String latitude, String longitude, List<BillData> bills) {
        this.user_id = user_id;
        this.client_id = client_id;
        this.total = total;
        this.discount = discount;
        this.total_after_discount = total_after_discount;
        this.notes = notes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bills = bills;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal_after_discount() {
        return total_after_discount;
    }

    public void setTotal_after_discount(String total_after_discount) {
        this.total_after_discount = total_after_discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<BillData> getBills() {
        return bills;
    }

    public void setBills(List<BillData> bills) {
        this.bills = bills;
    }

    public static class BillData implements Serializable{
        private String bill_id;
        private String paid_amount;

        public BillData(String bill_id, String paid_amount) {
            this.bill_id = bill_id;
            this.paid_amount = paid_amount;
        }

        public String getBill_id() {
            return bill_id;
        }

        public void setBill_id(String bill_id) {
            this.bill_id = bill_id;
        }

        public String getPaid_amount() {
            return paid_amount;
        }

        public void setPaid_amount(String paid_amount) {
            this.paid_amount = paid_amount;
        }
    }
}
