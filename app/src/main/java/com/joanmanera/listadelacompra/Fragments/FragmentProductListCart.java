package com.joanmanera.listadelacompra.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Adapters.AdapterProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class FragmentProductListCart extends Fragment {
    public static final int SPAN_COUNT = 3;
    private ArrayList<Product> products;
    private ArrayList<Category> categories;
    private AdapterProductList adapterProductList;
    private RecyclerView rvList;
    private EditText etFilter;
    private Button bAddProducts;
    private Intent intentCategoryList;
    private SQLiteHelper sqLiteHelper;
    private IProductListListener listener;
    private View.OnClickListener buttonAddProductListener;

    public FragmentProductListCart(IProductListListener listener, View.OnClickListener buttonAddProductListener, ArrayList<Product> products){
        this.listener = listener;
        this.buttonAddProductListener = buttonAddProductListener;
        this.products = products;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list_cart, container, false);

        sqLiteHelper = SQLiteHelper.getInstance(getActivity());
        categories = sqLiteHelper.getCategorias();
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
        bAddProducts = view.findViewById(R.id.bAddProducts);

        bAddProducts.setOnClickListener(buttonAddProductListener);

        adapterProductList = new AdapterProductList(products, listener, getActivity(), R.layout.item_product_list);
        rvList.setAdapter(adapterProductList);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterProductList.setProducts(sqLiteHelper.getListas().get(sqLiteHelper.getCurrentList()).getProducts());
    }

    public void refresh(){
        adapterProductList.notifyDataSetChanged();
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
}
