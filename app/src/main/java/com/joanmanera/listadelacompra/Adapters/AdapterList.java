package com.joanmanera.listadelacompra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Interfaces.IListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ListViewHolder>{
    private ArrayList<List> lists;
    private IListListener listener;

    public AdapterList(ArrayList<List> lists, IListListener listener){
        this.lists = lists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new AdapterList.ListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bindCategory(position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setLists(ArrayList<List> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNameList, tvDataList, tvProductCountList;
        private IListListener listener;

        public ListViewHolder(View view, IListListener listener){
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);

            tvNameList = view.findViewById(R.id.tvNameList);
            tvDataList = view.findViewById(R.id.tvDataList);
            tvProductCountList = view.findViewById(R.id.tvProductsCountList);
        }

        public void bindCategory(int position){
            List list = lists.get(position);

            tvNameList.setText(list.getName());
            tvDataList.setText(list.getDate());
            tvProductCountList.setText(String.valueOf(list.getProducts().size()));

        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onListSelected(getAdapterPosition());
            }
        }
    }
}
