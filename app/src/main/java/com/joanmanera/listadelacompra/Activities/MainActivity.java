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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
