package com.joanmanera.listadelacompra.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String name;
    private int image;
    private ArrayList<Product> products;

    public Category(String name, int image, ArrayList<Product> products) {
        this.name = name;
        this.image = image;
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
