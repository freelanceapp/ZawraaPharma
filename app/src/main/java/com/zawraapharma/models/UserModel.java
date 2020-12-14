package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public static class User implements Serializable {
        private int id;
        private String name;
        private String email;
        private String city;
        private String access_code;
        private String image;
        private String logo;
        private String token;
        private String latitude;
        private String longitude;
        private String address;
        private String user_type;
        private String details;
        private String fireBaseToken;

        public User() {
        }

        public User(int id, String name, String access_code, String logo, String token) {
            this.id = id;
            this.name = name;
            this.access_code = access_code;
            this.logo = logo;
            this.token = token;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getCity() {
            return city;
        }


        public String getAccess_code() {
            return access_code;
        }

        public String getImage() {
            return image;
        }

        public String getLogo() {
            return logo;
        }

        public String getToken() {
            return token;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return user_type;
        }

        public String getFireBaseToken() {
            return fireBaseToken;
        }

        public void setFireBaseToken(String fireBaseToken) {
            this.fireBaseToken = fireBaseToken;
        }

        public String getDetails() {
            return details;
        }



    }
}
