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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Adapters.AdapterList;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;
import com.joanmanera.listadelacompra.SQLiteHelper;

import java.util.ArrayList;

public class FragmentList extends Fragment {
    private ArrayList<List> lists;
    private AdapterList adapterList;
    private RecyclerView rvList;
    private SQLiteHelper sqLiteHelper;
    private EditText etFilter, etNameList;
    private Button bAddList;
    private Intent getIntentProductListCart;
    private IListListener listener;

    public FragmentList (IListListener listListener){
        this.listener = listListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        sqLiteHelper = SQLiteHelper.getInstance(getActivity());
        lists = sqLiteHelper.getListas();

        rvList = view.findViewById(R.id.rvList);
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

        etNameList = view.findViewById(R.id.etNameList);
        bAddList = view.findViewById(R.id.bAddList);
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

        adapterList = new AdapterList(lists, listener);
        rvList.setAdapter(adapterList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapterList.setLists(lists);


        return view;
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
    public void onResume() {
        super.onResume();
        adapterList.notifyDataSetChanged();
    }
}
