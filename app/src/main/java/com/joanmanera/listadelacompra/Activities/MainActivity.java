package com.joanmanera.listadelacompra.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Category> categories;
    private ArrayList<List> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("prod1", R.drawable.carne));
        products.add(new Product("prod2", R.drawable.pescado));

        categories = new ArrayList<>();
        categories.add(new Category("Carne", R.drawable.carne, products));
        categories.add(new Category("Pescado", R.drawable.pescado, products));

        lists = new ArrayList<>();
        lists.add(new List("lista 1", products));
        lists.add(new List("lista 2", new ArrayList<Product>()));
        lists.add(new List("lista 3", new ArrayList<Product>()));
        lists.add(new List("lista 4", new ArrayList<Product>()));
        lists.add(new List("lista 5", new ArrayList<Product>()));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                i.putExtra(ListActivity.EXTRA_LIST, lists);
                startActivityForResult(i, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            List list = (List)data.getSerializableExtra("list");
            Toast.makeText(this, list.getName(), Toast.LENGTH_LONG).show();

            Intent cat = new Intent(this, ListCategoryActivity.class);
            cat.putExtra(ListCategoryActivity.EXTRA_LIST_CATEGORY, categories);
            cat.putExtra(ListCategoryActivity.EXTRA_LIST, list);
            startActivity(cat);
        }
    }
}
