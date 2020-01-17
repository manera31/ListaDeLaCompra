package com.joanmanera.listadelacompra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.CategoryListViewHolder> implements View.OnClickListener {

    private ArrayList<Category> categories;
    private Context context;
    private View.OnClickListener listener;

    public AdapterCategoryList(ArrayList<Category> categories, Context context){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new CategoryListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        holder.bindCategory(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            listener.onClick(view);
        }
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView tvName;
        private Context context;

        public CategoryListViewHolder(View view, Context context){
            super(view);
            this.context = context;

            ivImage = view.findViewById(R.id.ivImage);
            tvName = view.findViewById(R.id.tvName);
        }

        public void bindCategory(int position){
            Category category = categories.get(position);
            ivImage.setImageResource(category.getImage());
            tvName.setText(category.getName());

        }
    }
}
