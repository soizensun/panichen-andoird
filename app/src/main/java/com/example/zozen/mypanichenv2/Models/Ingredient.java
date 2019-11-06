package com.example.zozen.mypanichenv2.Models;

public class Ingredient {
    private String name;
    private Double amount;
    private String unit;
    private String id;
    private String user;

    public Ingredient(String user, String name, Double amount, String unit) {
        this.user = user;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient(String user, String name, Double amount, String unit, String id) {
        this.user = user;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.id = id;
    }

    public Ingredient(){}

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", id=" + id +
                '}';
    }



}
