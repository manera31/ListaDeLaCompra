package com.joanmanera.listadelacompra.Fragments;

import android.app.Activity;
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

import com.joanmanera.listadelacompra.Activities.ListCategoryActivity;
import com.joanmanera.listadelacompra.Activities.ListProductActivity;
import com.joanmanera.listadelacompra.Adapters.AdapterProductList;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class FragmrntProductListCart extends Fragment {
    public static final int SPAN_COUNT = 3;
    private ArrayList<Product> products;
    private ArrayList<Category> categories;
    private ArrayList<List> lists;
    private AdapterProductList adapterProductList;
    private RecyclerView rvList;
    private EditText etFilter;
    private Button bAddProducts;
    private Intent intentCategoryList, intentProductList;
    private int selectedCategory, selectedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list_cart, container, false);

        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(getActivity());
        categories = sqLiteHelper.getCategorias();
        lists = sqLiteHelper.getListas();
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
        bAddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarIntentCategoryList();
            }
        });


        return view;
    }

    private void lanzarIntentCategoryList(){
        intentCategoryList = new Intent(getActivity(), ListCategoryActivity.class);
        intentCategoryList.putExtra(ListCategoryActivity.EXTRA_LIST_CATEGORY, categories);
        startActivityForResult(intentCategoryList, 1);
    }

    private void lanzarIntentProductList(){
        intentProductList = new Intent(getActivity(), ListProductActivity.class);
        intentProductList.putExtra(ListProductActivity.EXTRA_LIST_PRODUCT, categories.get(selectedCategory).getProducts());
        startActivityForResult(intentProductList, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    selectedCategory = data.getIntExtra("category", -1);
                    lanzarIntentProductList();
                    break;
                case 2:
                    Product selectedProduct = (Product) data.getSerializableExtra("product");
                    boolean isinList = false;

                    for (Product p: lists.get(selectedList).getProducts()){
                        if(p.getName().equals(selectedProduct.getName())){
                            isinList = true;
                        }
                    }

                    if (isinList){
                        Product productRemove = null;

                        for (Product p: lists.get(selectedList).getProducts()){
                            if(p.getName().equals(selectedProduct.getName())){
                                productRemove = p;
                            }
                        }

                        lists.get(selectedList).getProducts().remove(productRemove);
                    } else {
                        lists.get(selectedList).getProducts().add(selectedProduct);
                    }
                    break;
            }
        }
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
