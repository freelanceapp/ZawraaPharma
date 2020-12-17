package com.zawraapharma.models;

import java.io.Serializable;

public class BillModel implements Serializable {
    private String bill_id;
    private String paid_amount;

    public BillModel() {
    }

    public BillModel(String bill_id, String paid_amount) {
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
