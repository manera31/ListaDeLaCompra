package com.joanmanera.listadelacompra.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity implements IProductListListener {
    public static final String EXTRA_LIST_PRODUCT= "com.joanmanera.listadelacompra.EXTRA";
    public static final String EXTRA_LIST = "com.joanmanera.listadelacompra.LIST";
    private ArrayList<Product> products;
    private List list;
    private FragmentProductList fragmentProductList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        fragmentProductList = (FragmentProductList) getSupportFragmentManager().findFragmentById(R.id.fProductList);
        fragmentProductList.setProductListListener(this);
        products = (ArrayList<Product>)getIntent().getSerializableExtra(EXTRA_LIST_PRODUCT);
        list = (List)getIntent().getSerializableExtra(EXTRA_LIST);
        fragmentProductList.show(products);
        fragmentProductList.setList(list);
        setTitle("Productos");

    }

    @Override
    public void onProductListSelected(Product productSelected, boolean isChecked) {
        boolean isInList = false;
        for (Product p: list.getProducts()){
            if (productSelected.getName().toLowerCase().equals(p.getName().toLowerCase())) {
                isInList = true;
            }
        }

        if (isChecked){
            if (!isInList){
                list.getProducts().add(productSelected);
                //fragmentProductList.refreshAdapter();
                Toast.makeText(this, productSelected.getName()+", esta seleccionado", Toast.LENGTH_SHORT).show();
            }


        } else {
            if (isInList){
                list.getProducts().remove(productSelected);
                //fragmentProductList.refreshAdapter();
                Toast.makeText(this, productSelected.getName()+", no esta seleccionado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
