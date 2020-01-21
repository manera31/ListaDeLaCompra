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
    private List selectedList;
    private Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Product> products1 = new ArrayList<>();
        products1.add(new Product("prod1", R.drawable.carne));
        products1.add(new Product("prod2", R.drawable.pescado));
        ArrayList<Product> products2 = new ArrayList<>();
        products1.add(new Product("prod3", R.drawable.carne));
        products1.add(new Product("prod4", R.drawable.pescado));

        categories = new ArrayList<>();
        categories.add(new Category("Carne", R.drawable.carne, products1));
        categories.add(new Category("Pescado", R.drawable.pescado, products2));

        lists = new ArrayList<>();
        lists.add(new List("lista 1", products1));
        lists.add(new List("lista 2", new ArrayList<Product>()));
        lists.add(new List("lista 3", new ArrayList<Product>()));
        lists.add(new List("lista 4", new ArrayList<Product>()));
        lists.add(new List("lista 5", new ArrayList<Product>()));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarIntentList();
            }
        });

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
        intentProductList.putExtra(ListProductActivity.EXTRA_LIST_PRODUCT, selectedCategory.getProducts());
        startActivityForResult(intentProductList, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    selectedList = (List) data.getSerializableExtra("list");
                    lanzarIntentCategoryList();
                    break;
                case 1:
                    selectedCategory = (Category) data.getSerializableExtra("category");
                    lanzarIntentProductList();
                    break;
                case 2:
                    Product selectedProduct = (Product) data.getSerializableExtra("product");
                    boolean isChecked = data.getBooleanExtra("isChecked", false);
                    Toast.makeText(this, selectedProduct.getName() + ", " + isChecked, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        /*if (resultCode == Activity.RESULT_OK){
            list = (List)data.getSerializableExtra("list");
            //Toast.makeText(this, list.getName(), Toast.LENGTH_LONG).show();

            Intent cat = new Intent(this, ListCategoryActivity.class);
            cat.putExtra(ListCategoryActivity.EXTRA_LIST_CATEGORY, categories);
            cat.putExtra(ListCategoryActivity.EXTRA_LIST, (Serializable) listener);
            startActivity(cat);
        }*/
    }
}
