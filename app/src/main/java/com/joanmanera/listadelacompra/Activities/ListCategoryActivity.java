package com.joanmanera.listadelacompra.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentCategoryList;
import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity implements ICategoryListListener {
    public static final String EXTRA_LIST_CATEGORY = "com.joanmanera.listadelacompra.CATEGORIES";
    private ArrayList<Category> categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        FragmentCategoryList fragmentCategoryList = (FragmentCategoryList)getSupportFragmentManager().findFragmentById(R.id.fCategoryList);
        fragmentCategoryList.setCategoryListListener(this);
        categories = (ArrayList<Category>)getIntent().getSerializableExtra(EXTRA_LIST_CATEGORY);
        fragmentCategoryList.show(categories);
        setTitle("Categorías");

    }

    @Override
    public void onCategoryListSelected(int category) {
        Intent result = new Intent();
        result.putExtra("category", category);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
