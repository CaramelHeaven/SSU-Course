package com.caramelheaven.gymdatabase.datasourse.model;

public class GymMembership {

    private String day_of_end;
    private String day_of_start;
    private String id_gym_membership;
    private String price_of_membership;
    private String sale;

    public GymMembership(String day_of_end, String day_of_start, String id_gym_membership, String price_of_membership, String sale) {
        this.day_of_end = day_of_end;
        this.day_of_start = day_of_start;
        this.id_gym_membership = id_gym_membership;
        this.price_of_membership = price_of_membership;
        this.sale = sale;
    }

    public GymMembership() {

    }

    public String getDay_of_end() {
        return day_of_end;
    }

    public String getDay_of_start() {
        return day_of_start;
    }

    public String getId_gym_membership() {
        return id_gym_membership;
    }

    public String getPrice_of_membership() {
        return price_of_membership;
    }

    public String getSale() {
        return sale;
    }

    public void setDay_of_end(String day_of_end) {
        this.day_of_end = day_of_end;
    }

    public void setDay_of_start(String day_of_start) {
        this.day_of_start = day_of_start;
    }

    public void setId_gym_membership(String id_gym_membership) {
        this.id_gym_membership = id_gym_membership;
    }

    public void setPrice_of_membership(String price_of_membership) {
        this.price_of_membership = price_of_membership;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}