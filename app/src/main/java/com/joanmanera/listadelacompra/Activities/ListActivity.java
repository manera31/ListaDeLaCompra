package com.joanmanera.listadelacompra.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.Fragments.FragmentCategoryList;
import com.joanmanera.listadelacompra.Fragments.FragmentList;
import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements IListListener {
    public static final String EXTRA_LIST = "com.joanmanera.listadelacompra.LISTS";
    private ArrayList<List> lists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentList fragmentList = (FragmentList)getSupportFragmentManager().findFragmentById(R.id.fList);
        fragmentList.setListListener(this);
        lists = (ArrayList<List>)getIntent().getSerializableExtra(EXTRA_LIST);
        fragmentList.show(lists);
        setTitle("Listas");
    }


    @Override
    public void onListSelected(List list) {
        Intent result = new Intent();
        result.putExtra("list", list);
        setResult(Activity.RESULT_OK, result);
        finish();

    }
}
