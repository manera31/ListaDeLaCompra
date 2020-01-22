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

import java.util.ArrayList;

public class FragmentCategoryList extends Fragment {

    public static final int SPAN_COUNT = 3;
    private ArrayList<Category> categories;
    private AdapterCategoryList adapterCategoryList;
    private RecyclerView rvList;
    private EditText etFilter;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_category_list, container, false);

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

        LinearLayout llAddList = view.findViewById(R.id.llAddList);
        llAddList.setVisibility(View.INVISIBLE);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void show(ArrayList<Category> categories){
        this.categories = categories;
        adapterCategoryList.setCategories(categories);
    }

    private void filter(String s){
        ArrayList<Category> filteredCategories = new ArrayList<>();
        for (Category c: categories){
            if(c.getName().toLowerCase().contains(s.toLowerCase())){
                filteredCategories.add(c);
            }
        }

        adapterCategoryList.setCategories(filteredCategories);

    }

    public void setCategoryListListener(ICategoryListListener listener){
        adapterCategoryList = new AdapterCategoryList(categories, listener);
        rvList.setAdapter(adapterCategoryList);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
    }
}
