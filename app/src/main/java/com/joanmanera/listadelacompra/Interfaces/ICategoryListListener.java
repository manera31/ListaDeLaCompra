package com.joanmanera.listadelacompra.Interfaces;

import com.joanmanera.listadelacompra.Models.Product;

import java.util.ArrayList;

public interface ICategoryListListener {
    void onCategoryListSelected(ArrayList<Product> products);
}
