package com.zawraapharma.models;

import java.io.Serializable;

public class CompanyProductModel implements Serializable {
    private int id;
    private String title;
    private String item_code;
    private String company_id;
    private String price;
    private String amount="";
    private boolean isSelected = false;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
