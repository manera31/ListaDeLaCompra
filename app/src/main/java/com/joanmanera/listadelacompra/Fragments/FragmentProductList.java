package com.joanmanera.listadelacompra.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Adapters.AdapterProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class FragmentProductList extends Fragment {
    public static final int SPAN_COUNT = 3;
    private ArrayList<Product> products;
    private AdapterProductList adapterProductList;
    private RecyclerView rvList;
    private EditText etFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_category_list, container, false);

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

    public void show(ArrayList<Product> products){
        this.products = products;
        adapterProductList.setProducts(products);
    }

    private void filter(String s){
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product p: products){
            if(p.getName().toLowerCase().contains(s.toLowerCase())){
                filteredProducts.add(p);
            }
        }

        adapterProductList.setProducts(filteredProducts);

    }

    public void setProductListListener(IProductListListener listener){
        adapterProductList = new AdapterProductList(products, listener, getActivity(), R.layout.item_product_list);
        rvList.setAdapter(adapterProductList);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
    }
}
