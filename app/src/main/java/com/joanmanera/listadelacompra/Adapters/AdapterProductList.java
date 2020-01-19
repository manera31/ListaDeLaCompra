package com.joanmanera.listadelacompra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joanmanera.listadelacompra.Activities.ListProductActivity;
import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ProductViewHolder> {

    private ArrayList<Product> products;
    private IProductListListener listener;
    private Context context;

    public AdapterProductList(ArrayList<Product> products, IProductListListener listener, Context context){
        this.products = products;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_product_list, parent, false);
        return new ProductViewHolder(view, listener, context);
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

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivImage;
        private TextView tvName;
        private IProductListListener listener;
        private Context context;

        public ProductViewHolder(View view, IProductListListener listener, Context context){
            super(view);
            this.listener = listener;
            this.context = context;
            view.setOnClickListener(this);
            ivImage = view.findViewById(R.id.ivImage);
            tvName = view.findViewById(R.id.tvName);
        }

        public void bindCategory(int position){
            Product product = products.get(position);
            ivImage.setImageResource(product.getImage());
            tvName.setText(product.getName());
        }

        @Override
        public void onClick(View v) {
            //Glide.with(context).load(R.drawable.baseline_done_24).into(ivImage);
            listener.onProductListSelected(products.get(getAdapterPosition()));
        }
    }
}
