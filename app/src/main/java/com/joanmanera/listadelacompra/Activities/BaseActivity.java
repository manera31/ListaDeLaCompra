package com.joanmanera.listadelacompra.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joanmanera.listadelacompra.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected FrameLayout container;
    protected int idAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        container = findViewById(R.id.container);
    }
}
