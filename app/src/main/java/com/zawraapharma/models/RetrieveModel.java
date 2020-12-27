package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class RetrieveModel implements Serializable {
    private String bill_code;
    private String company_id;
    private String user_id;
    private String client_id;
    private List<Product_Bill_Model> items;

    public RetrieveModel(String bill_code, String company_id, String user_id, String client_id, List<Product_Bill_Model> items) {
        this.bill_code = bill_code;
        this.company_id = company_id;
        this.user_id = user_id;
        this.client_id = client_id;
        this.items = items;
    }

    public String getBill_code() {
        return bill_code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public List<Product_Bill_Model> getItems() {
        return items;
    }
}
