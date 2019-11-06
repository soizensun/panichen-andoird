package com.example.zozen.mypanichenv2.Models;

import java.util.List;

public class Menu {
    private List<Ingredient> ListIngredient;
    private String name;
    private String type;
    private String id;


    public Menu(List<Ingredient> listIngredient, String name, String type) {
        ListIngredient = listIngredient;
        this.name = name;
        this.type = type;
    }

    public Menu(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Menu(String name, String type, String id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Ingredient> getListIngredient() {
        return ListIngredient;
    }

    @Override
    public String toString() {
        return "name : " + this.name + "class !!!!!";
    }

    public String getID() {
        return id;
    }
}
