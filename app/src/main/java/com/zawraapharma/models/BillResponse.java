package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class BillResponse implements Serializable {
    private int status;
    private Data data;

    public int getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private int id;
        private int client_id;
        private int user_id;
        private String date;
        private String time;
        private double total;
        private double discount;
        private double total_after_discount;
        private String is_paid_to_admin_at;
        private String notes;
        private String created_at;
        private String updated_at;
        private List<Bill> bills;
        private UserFk user_fk;
        private ClientFk client_fk;

        public int getId() {
            return id;
        }

        public int getClient_id() {
            return client_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public double getTotal() {
            return total;
        }

        public double getDiscount() {
            return discount;
        }

        public double getTotal_after_discount() {
            return total_after_discount;
        }

        public String getIs_paid_to_admin_at() {
            return is_paid_to_admin_at;
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

        public List<Bill> getBills() {
            return bills;
        }

        public UserFk getUser_fk() {
            return user_fk;
        }

        public ClientFk getClient_fk() {
            return client_fk;
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

        public static class BillFk implements Serializable{
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
            private CompanyFk company_fk;

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

            public CompanyFk getCompany_fk() {
                return company_fk;
            }
        }

        public static class Bill implements Serializable{
            private int id;
            private int payment_id;
            private int bill_id;
            private int client_id;
            private int user_id;
            private double paid_amount;
            private String date;
            private String created_at;
            private String updated_at;
            private BillFk bill_fk;

            public int getId() {
                return id;
            }

            public int getPayment_id() {
                return payment_id;
            }

            public int getBill_id() {
                return bill_id;
            }

            public int getClient_id() {
                return client_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public double getPaid_amount() {
                return paid_amount;
            }

            public String getDate() {
                return date;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public BillFk getBill_fk() {
                return bill_fk;
            }
        }

        public static class UserFk implements Serializable{
            private int id;
            private String name;
            private String phone;
            private String access_code;
            private int balance;
            private Object email;
            private String logo;
            private String address;
            private String latitude;
            private String longitude;
            private String is_block;
            private String is_login;
            private String logout_time;
            private String notification_status;
            private String email_verification_code;
            private String email_verified_at;
            private String deleted_at;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }

            public String getAccess_code() {
                return access_code;
            }

            public int getBalance() {
                return balance;
            }

            public Object getEmail() {
                return email;
            }

            public String getLogo() {
                return logo;
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

            public String getIs_block() {
                return is_block;
            }

            public String getIs_login() {
                return is_login;
            }

            public String getLogout_time() {
                return logout_time;
            }

            public String getNotification_status() {
                return notification_status;
            }

            public String getEmail_verification_code() {
                return email_verification_code;
            }

            public String getEmail_verified_at() {
                return email_verified_at;
            }

            public String getDeleted_at() {
                return deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
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
            private String bill_code;

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

            public String getBill_code() {
                return bill_code;
            }
        }


    }
}
