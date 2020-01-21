package com.joanmanera.listadelacompra.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity implements IProductListListener {
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.EXTRA";
    public static final String EXTRA_LIST = "com.joanmanera.listadelacompra.LIST";
    private ArrayList<Product> products;
    private IProductListListener listener;
    private FragmentProductList fragmentProductList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        fragmentProductList = (FragmentProductList) getSupportFragmentManager().findFragmentById(R.id.fProductList);

        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        listener = (IProductListListener) getIntent().getSerializableExtra(EXTRA_LIST);
        fragmentProductList.setProductListListener(listener);
        fragmentProductList.show(products);
        //fragmentProductList.setList(list);
        setTitle("Productos");

    }

    public void onProductListSelected(Product productSelected, boolean isChecked) {
        Intent result = new Intent();
        result.putExtra("product", productSelected);
        result.putExtra("isChecked", isChecked);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
