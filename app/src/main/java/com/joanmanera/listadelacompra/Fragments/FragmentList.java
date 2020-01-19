package com.joanmanera.listadelacompra.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Adapters.AdapterList;
import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class FragmentList extends Fragment {
    private ArrayList<List> lists;
    private AdapterList adapterList;
    private RecyclerView rvList;
    private EditText etFilter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

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
        //adapterCategoryList = new AdapterCategoryList(categories, getActivity(), listener);
        //rvList.setAdapter(adapterCategoryList);
        //rvList.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void show(ArrayList<List> lists){
        this.lists = lists;
        adapterList.setLists(lists);
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

    public void setListListener(IListListener listener){
        adapterList = new AdapterList(lists, listener);
        rvList.setAdapter(adapterList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }
}
