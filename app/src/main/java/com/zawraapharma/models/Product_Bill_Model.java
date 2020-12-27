package com.zawraapharma.models;

import java.io.Serializable;

public class Product_Bill_Model implements Serializable {
    private String item_id;
    private double back_amount;

    public Product_Bill_Model(String item_id, double back_amount) {
        this.item_id = item_id;
        this.back_amount = back_amount;
    }

    public String getItem_id() {
        return item_id;
    }

    public double getBack_amount() {
        return back_amount;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setBack_amount(double back_amount) {
        this.back_amount = back_amount;
    }
}
