package com.zawraapharma.models;

import java.io.Serializable;

public class BillResponse implements Serializable {


    public static class Data implements Serializable{
        private int id;
        private int client_id;
        private int user_id;
        private String date;
        private String time;
        private int total;
        private int discount;
        private int total_after_discount;
        private String is_paid_to_admin_at;
        private String notes;
        private String created_at;
        private String updated_at;
        private UserModel.User user_fk;
        private PharmacyModel client_fk;
    }
}
