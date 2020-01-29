package com.joanmanera.listadelacompra.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Adapters.AdapterCategoryList;
import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class FragmentCategoryList extends Fragment {

    public static final int SPAN_COUNT = 3;
    private AdapterCategoryList adapterCategoryList;
    private RecyclerView rvList;
    private EditText etFilter;
    private ICategoryListListener listener;
    private SQLiteHelper sqLiteHelper;

    public FragmentCategoryList(ICategoryListListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_category, container, false);

        sqLiteHelper = SQLiteHelper.getInstance(getActivity());

        etFilter = view.findViewById(R.id.etFilter);
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

        rvList = view.findViewById(R.id.rvList);

        adapterCategoryList = new AdapterCategoryList(sqLiteHelper.getCategorias(), listener);
        rvList.setAdapter(adapterCategoryList);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void filter(String s){
        ArrayList<Category> filteredCategories = new ArrayList<>();
        for (Category c: sqLiteHelper.getCategorias()){
            if(c.getName().toLowerCase().contains(s.toLowerCase())){
                filteredCategories.add(c);
            }
        }

        adapterCategoryList.setCategories(filteredCategories);

    }
}
