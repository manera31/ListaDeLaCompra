package com.joanmanera.listadelacompra.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joanmanera.listadelacompra.Adapters.AdapterList;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IListListener {
    private ArrayList<Category> categories;
    private ArrayList<List> lists;

    //private Intent intentList;
    private Intent intentCategoryList;
    private Intent intentProductList;
    private Intent getIntentProductListCart;
    private int selectedList;
    private int selectedCategory;


    private AdapterList adapterList;
    private RecyclerView rvList;
    private EditText etFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(this);
        if (sqLiteHelper.cargarDatos()){
            Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos no cargados", Toast.LENGTH_SHORT).show();
        }

        lists = sqLiteHelper.getListas();
        categories = sqLiteHelper.getCategorias();

        rvList = findViewById(R.id.rvList);
        etFilter = findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        adapterList = new AdapterList(lists, this);
        rvList.setAdapter(adapterList);
        rvList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterList.setLists(lists);
        setTitle("Listas");

    }

    private void filter(String s){
        ArrayList<List> filteredLists = new ArrayList<>();
        for (List l: lists){
            if(l.getName().toLowerCase().contains(s.toLowerCase())){
                filteredLists.add(l);
            }
        }

        adapterList.setLists(filteredLists);
    }

    private void lanzarIntentCategoryList(){
        intentCategoryList = new Intent(this, ListCategoryActivity.class);
        intentCategoryList.putExtra(ListCategoryActivity.EXTRA_LIST_CATEGORY, categories);
        startActivityForResult(intentCategoryList, 1);
    }

    private void lanzarIntentProductList(){
        intentProductList = new Intent(this, ListProductActivity.class);
        intentProductList.putExtra(ListProductActivity.EXTRA_LIST_PRODUCT, categories.get(selectedCategory).getProducts());
        startActivityForResult(intentProductList, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    selectedList = data.getIntExtra("list", -1);
                    lanzarIntentCategoryList();
                    break;
                case 1:
                    selectedCategory = data.getIntExtra("category", -1);
                    lanzarIntentProductList();
                    break;
                case 2:
                    Product selectedProduct = (Product) data.getSerializableExtra("product");
                    boolean isinList = false;

                    for (Product p: lists.get(selectedList).getProducts()){
                        if(p.getName().equals(selectedProduct.getName())){
                            isinList = true;
                        }
                    }

                    if (isinList){
                        Product productRemove = null;

                        for (Product p: lists.get(selectedList).getProducts()){
                            if(p.getName().equals(selectedProduct.getName())){
                                productRemove = p;
                            }
                        }

                        lists.get(selectedList).getProducts().remove(productRemove);
                    } else {
                        lists.get(selectedList).getProducts().add(selectedProduct);
                    }

                    Toast.makeText(this, selectedProduct.getName() , Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    selectedList = data.getIntExtra("list", -1);
                    intentProductList = new Intent(MainActivity.this, ListProductActivity.class);
                    intentProductList.putExtra(ListProductActivity.EXTRA_LIST_PRODUCT, lists.get(selectedList).getProducts());
                    startActivity(intentProductList);
                    break;
            }
        }*/
    }

    @Override
    public void onListSelected(int list) {
        this.selectedList = list;
        Toast.makeText(this, String.valueOf(list), Toast.LENGTH_SHORT).show();

        getIntentProductListCart = new Intent(this, ListProductCartActivity.class);
        getIntentProductListCart.putExtra(ListProductCartActivity.EXTRA_LIST_PRODUCT, lists.get(selectedList).getProducts());
        startActivity(getIntentProductListCart);
    }
}
