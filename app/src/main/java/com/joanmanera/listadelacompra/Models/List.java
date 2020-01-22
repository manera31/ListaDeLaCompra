package com.joanmanera.listadelacompra.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class List implements Serializable {
    private String name;
    private String date;
    private ArrayList<Product> products;

    public List(String name, ArrayList<Product> products) {
        this.name = name;
        this.date = new GregorianCalendar().getTime().toString();
        this.products = products;
    }

    public List(String name, String date, ArrayList<Product> products) {
        this.name = name;
        this.date = date;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
