package com.joanmanera.listadelacompra.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.joanmanera.listadelacompra.Fragments.FragmentCategoryList;
import com.joanmanera.listadelacompra.Fragments.FragmentList;
import com.joanmanera.listadelacompra.Fragments.FragmentProductList;
import com.joanmanera.listadelacompra.Fragments.FragmentProductListCart;
import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

public class MainActivity extends AppCompatActivity implements IListListener, ICategoryListListener, IProductListListener, View.OnClickListener {
    private SQLiteHelper sqLiteHelper;
    private FragmentProductListCart fragmentProductListCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = SQLiteHelper.getInstance(this);
        if (sqLiteHelper.cargarDatos()){
            Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos no cargados", Toast.LENGTH_SHORT).show();
        }

        //TODO llamar fragment
        FragmentList fragmentList = new FragmentList(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmentList).addToBackStack(null).commit();
        setTitle("Listas");

    }

    @Override
    public void onListSelected(int list) {
        fragmentProductListCart = new FragmentProductListCart(this, this, sqLiteHelper.getListas().get(list).getProducts());
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmentProductListCart).addToBackStack(null).commit();
        setTitle("Productos de la lista");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bAddProducts){
            FragmentCategoryList fragmentCategoryList = new FragmentCategoryList(this, sqLiteHelper.getCategorias());
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmentCategoryList).addToBackStack(null).commit();
            setTitle("Categorías");
        }
    }

    @Override
    public void onCategoryListSelected(int category) {
        FragmentProductList fragmentProductList = new FragmentProductList(this, sqLiteHelper.getCategorias().get(category).getProducts());
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmentProductList).addToBackStack(null).commit();
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

        fragmentProductListCart.refresh();
    }
}
