package com.joanmanera.listadelacompra.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity implements IProductListListener {
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.EXTRA";
    private ArrayList<Product> products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        FragmentProductList fragmentProductList = (FragmentProductList) getSupportFragmentManager().findFragmentById(R.id.fProductList);
        fragmentProductList.setProductListListener(this);
        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        fragmentProductList.show(products);
        setTitle("Productos");

    }

    @Override
    public void onProductListSelected(Product productSelected) {
        Toast.makeText(this, productSelected.getName(), Toast.LENGTH_SHORT).show();
    }
}
