package com.zawraapharma.models;

import java.io.Serializable;

public class InvoiceModel implements Serializable {
    private int id;
    private String code;
    private int client_id;
    private String user_id;
    private int company_id;
    private String date;
    private int total;
    private int debt_amount;
    private int paid;
    private int remaining;
    private String status;
    private String is_exceed_90_days;
    private String notes;
    private String  created_at;
    private String updated_at;
    private String amount="";
    private CompanyFk company_fk;
    private boolean isSelected = false;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public int getDebt_amount() {
        return debt_amount;
    }

    public int getPaid() {
        return paid;
    }

    public int getRemaining() {
        return remaining;
    }

    public String getStatus() {
        return status;
    }

    public String getIs_exceed_90_days() {
        return is_exceed_90_days;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public CompanyFk getCompany_fk() {
        return company_fk;
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

    public static class CompanyFk implements Serializable{
        private int id;
        private String title;
        private String code;
        private String logo;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCode() {
            return code;
        }

        public String getLogo() {
            return logo;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
