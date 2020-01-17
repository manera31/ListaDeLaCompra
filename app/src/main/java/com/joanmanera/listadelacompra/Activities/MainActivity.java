package com.joanmanera.listadelacompra.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories = new ArrayList<>();

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("producto1", R.drawable.pescado));
        products.add(new Product("producto2", R.drawable.carne));

        categories.add(new Category("carne", R.drawable.carne, products));
        categories.add(new Category("pescado", R.drawable.pescado, products));

        Button b = findViewById(R.id.bCategorias);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListCategoryActivity.class);
                i.putExtra(ListCategoryActivity.EXTRA_LIST_CATEGORY, categories);
                startActivity(i);

            }
        });
    }
}
