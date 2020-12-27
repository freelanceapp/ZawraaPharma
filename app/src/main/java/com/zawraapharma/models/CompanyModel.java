package com.zawraapharma.models;

import java.io.Serializable;

public class CompanyModel implements Serializable {
    private int id;
    private String title;
    private String code;
    private String logo;



    public CompanyModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

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
}
