package com.joanmanera.listadelacompra.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Category> categories;
    private ArrayList<List> lists;

    private Intent intentList;
    private Intent intentCategoryList;
    private Intent intentProductList;
    private int selectedList;
    private int selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product p1 = new Product("carne1", R.drawable.carne);
        Product p2 = new Product("pescado1", R.drawable.pescado);

        Product p3 = new Product("carne2", R.drawable.carne);
        Product p4 = new Product("pescado2", R.drawable.pescado);

        ArrayList<Product> productsCarne = new ArrayList<>();
        productsCarne.add(p1);
        productsCarne.add(p3);

        ArrayList<Product> productsPescado = new ArrayList<>();
        productsPescado.add(p2);
        productsPescado.add(p4);

        categories = new ArrayList<>();
        categories.add(new Category("Carne", R.drawable.carne, productsCarne));
        categories.add(new Category("Pescado", R.drawable.pescado, productsPescado));

        ArrayList<Product> prodlist = new ArrayList<>();
        prodlist.add(p3);
        prodlist.add(p4);

        lists = new ArrayList<>();
        lists.add(new List("lista 1", prodlist));
        lists.add(new List("lista 2", new ArrayList<Product>()));
        lists.add(new List("lista 3", new ArrayList<Product>()));
        lists.add(new List("lista 4", new ArrayList<Product>()));
        lists.add(new List("lista 5", new ArrayList<Product>()));

        intentList = new Intent(MainActivity.this, ListActivity.class);
        intentList.putExtra(ListActivity.EXTRA_LIST, lists);
        startActivityForResult(intentList, 3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarIntentList();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
    }

    private void lanzarIntentList(){
        intentList = new Intent(MainActivity.this, ListActivity.class);
        intentList.putExtra(ListActivity.EXTRA_LIST, lists);
        startActivityForResult(intentList, 0);
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

        if (resultCode == Activity.RESULT_OK) {
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
        }
    }
}
