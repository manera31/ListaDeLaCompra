package com.joanmanera.listadelacompra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Interfaces.ICategoryListListener;
import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.CategoryListViewHolder> {

    private ArrayList<Category> categories;
    private ICategoryListListener listener;

    public AdapterCategoryList(ArrayList<Category> categories, ICategoryListListener listener){
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_product_list, parent, false);
        return new CategoryListViewHolder(view, listener);
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

    public class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivImage;
        private TextView tvName;
        private ICategoryListListener listener;

        public CategoryListViewHolder(View view, ICategoryListListener listener){
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);

            ivImage = view.findViewById(R.id.ivImage);
            tvName = view.findViewById(R.id.tvName);
        }

        public void bindCategory(int position){
            Category category = categories.get(position);
            ivImage.setImageResource(category.getImage());
            tvName.setText(category.getName());

        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onCategoryListSelected(categories.get(getAdapterPosition()).getProducts());
            }
        }
    }
}
