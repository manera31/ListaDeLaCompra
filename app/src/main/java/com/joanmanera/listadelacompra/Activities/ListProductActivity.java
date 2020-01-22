package com.joanmanera.listadelacompra.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity implements IProductListListener {
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.PRODUCTS";
    private ArrayList<Product> products;
    private FragmentProductList fragmentProductList;
    private SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        fragmentProductList = (FragmentProductList) getSupportFragmentManager().findFragmentById(R.id.fProductList);
        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        fragmentProductList.setProductListListener(this);
        fragmentProductList.show(products);
        setTitle("Productos");

    }

    @Override
    public void onProductListSelected(Product productSelected) {

        Toast.makeText(this, productSelected.getName(), Toast.LENGTH_SHORT).show();

        sqLiteHelper = SQLiteHelper.getInstance(this);

        boolean encontrado = false;
        for (Product p: sqLiteHelper.getListas().get(sqLiteHelper.getCurrentList()).getProducts()){
            if (p.getName().equals(productSelected.getName())){
                encontrado = true;
            }
        }

        if (!encontrado){
            sqLiteHelper.addProductoLista(productSelected, sqLiteHelper.getListas().get(sqLiteHelper.getCurrentList()));
        } else {
            sqLiteHelper.removeProducto(productSelected, sqLiteHelper.getListas().get(sqLiteHelper.getCurrentList()));
        }
    }
}
