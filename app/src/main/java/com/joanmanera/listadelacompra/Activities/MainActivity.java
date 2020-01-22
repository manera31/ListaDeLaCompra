package com.joanmanera.listadelacompra.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joanmanera.listadelacompra.Adapters.AdapterList;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IListListener {
    private ArrayList<List> lists;

    private Intent getIntentProductListCart;

    private AdapterList adapterList;
    private RecyclerView rvList;
    private EditText etFilter, etNameList;
    private SQLiteHelper sqLiteHelper;
    private Button bAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_category_list);

        sqLiteHelper = SQLiteHelper.getInstance(this);
        if (sqLiteHelper.cargarDatos()){
            Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos no cargados", Toast.LENGTH_SHORT).show();
        }

        lists = sqLiteHelper.getListas();

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

        etNameList = findViewById(R.id.etNameList);
        bAddList = findViewById(R.id.bAddList);
        bAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNameList.getText().toString().equals("")){
                    sqLiteHelper.addLista(new List(etNameList.getText().toString()), new ArrayList<Product>());
                    adapterList.setLists(sqLiteHelper.getListas());
                    etNameList.setText("");
                }
            }
        });

        adapterList = new AdapterList(lists, this);
        rvList.setAdapter(adapterList);
        rvList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterList.setLists(lists);
        setTitle("Listas");

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterList.notifyDataSetChanged();
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

    @Override
    public void onListSelected(int list) {
        sqLiteHelper.setCurrentList(list);
        getIntentProductListCart = new Intent(this, ListProductCartActivity.class);
        getIntentProductListCart.putExtra(ListProductCartActivity.EXTRA_LIST_PRODUCT, list);
        startActivity(getIntentProductListCart);
    }
}
