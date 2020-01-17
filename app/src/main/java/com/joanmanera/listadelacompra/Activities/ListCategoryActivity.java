package com.joanmanera.listadelacompra.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentCategoryList;
import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity implements ICategoryListListener {
    public static final String EXTRA_LIST_CATEGORY = "com.joanmanera.listadelacompra.EXTRA";
    private ArrayList<Category> categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentCategoryList fragmentCategoryList = (FragmentCategoryList)getSupportFragmentManager().findFragmentById(R.id.fList);
        fragmentCategoryList.setCategoryListListener(this);
        categories = (ArrayList<Category>)getIntent().getSerializableExtra(EXTRA_LIST_CATEGORY);
        fragmentCategoryList.show(categories);
    }

    @Override
    public void onCategoryListSelected(ArrayList<Product> products) {
        Intent i = new Intent(this, ListProductActivity.class);
        i.putExtra(ListProductActivity.EXTRA_LIST_PRODUCT, products);
        startActivity(i);
    }
}
