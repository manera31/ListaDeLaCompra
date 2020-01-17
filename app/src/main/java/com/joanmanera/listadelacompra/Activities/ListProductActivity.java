package com.joanmanera.listadelacompra.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentCategoryList;
import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity{
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.EXTRA";
    private ArrayList<Product> products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentProductList fragmentProductList = (FragmentProductList) getSupportFragmentManager().findFragmentById(R.id.fList);
        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        fragmentProductList.show(products);
    }
}