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
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ProductViewHolder> {

    private ArrayList<Product> products;
    private Context context;

    public AdapterProductList(ArrayList<Product> products, Context context){
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindCategory(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView tvName;

        public ProductViewHolder(View view){
            super(view);
            ivImage = view.findViewById(R.id.ivImage);
            tvName = view.findViewById(R.id.tvName);
        }

        public void bindCategory(int position){
            Product product = products.get(position);
            //ivImage.setImageResource(product.getImage());
            tvName.setText(product.getName());
        }
    }
}
