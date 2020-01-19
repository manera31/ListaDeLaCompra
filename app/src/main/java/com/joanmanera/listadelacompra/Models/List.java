package com.joanmanera.listadelacompra.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class List implements Serializable {
    private String name;
    private GregorianCalendar date;
    private ArrayList<Product> products;

    public List(String name, ArrayList<Product> products) {
        this.name = name;
        this.date = new GregorianCalendar();
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
