package com.joanmanera.listadelacompra.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Fragments.FragmrntProductListCart;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListProductCartActivity extends AppCompatActivity {
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.PRODUCTS";
    private ArrayList<Product> products;
    private FragmrntProductListCart fragmrntProductListCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_cart);
        fragmrntProductListCart = (FragmrntProductListCart) getSupportFragmentManager().findFragmentById(R.id.fProductListCart);
        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        fragmrntProductListCart.setProductListListener(null);
        fragmrntProductListCart.show(products);
        setTitle("Productos de la lista");

    }
}
