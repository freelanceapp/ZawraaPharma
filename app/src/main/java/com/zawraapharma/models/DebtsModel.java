package com.zawraapharma.models;

import java.io.Serializable;

public class DebtsModel implements Serializable {
    private int id;
    private String code;
    private int client_id;
    private String user_id;
    private int company_id;
    private String date;
    private double total;
    private double debt_amount;
    private double paid;
    private double remaining;
    private String status;
    private String is_exceed_90_days;
    private String notes;
    private String created_at;
    private String updated_at;
    private PharmacyModel client_fk;

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

    public double getTotal() {
        return total;
    }

    public double getDebt_amount() {
        return debt_amount;
    }

    public double getPaid() {
        return paid;
    }

    public double getRemaining() {
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

    public PharmacyModel getClient_fk() {
        return client_fk;
    }

    public static class BillCode {
        private String code;

        public String getCode() {
            return code;
        }
    }

    public static class ClientFk implements Serializable{
        private int id;
        private String title;
        private String category_title;
        private int balance;
        private String address;
        private String latitude;
        private String longitude;
        private String logo;
        private String code;
        private String bills_count;
        private String created_at;
        private String updated_at;
        private BillCode bill_code;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory_title() {
            return category_title;
        }

        public int getBalance() {
            return balance;
        }

        public String getAddress() {
            return address;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLogo() {
            return logo;
        }

        public String getCode() {
            return code;
        }

        public String getBills_count() {
            return bills_count;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public BillCode getBill_code() {
            return bill_code;
        }
    }
}
