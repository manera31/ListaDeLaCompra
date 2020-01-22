package com.joanmanera.listadelacompra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joanmanera.listadelacompra.Interfaces.IProductListListener;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;
import com.joanmanera.listadelacompra.R;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ProductViewHolder> {

    private ArrayList<Product> products;
    private IProductListListener listener;
    private Context context;
    private int layout;

    public AdapterProductList(ArrayList<Product> products, IProductListListener listener, Context context, int layout){
        this.products = products;
        this.listener = listener;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
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
            ivImage = view.findViewById(R.id.ivProductImage);
            tvName = view.findViewById(R.id.tvNameProduct);
            view.setOnClickListener(this);
        }

        public void bindCategory(int position){
            Product product = products.get(position);
            ivImage.setImageResource(product.getImage());
            tvName.setText(product.getName());
        }

        @Override
        public void onClick(View v) {
            Product product = products.get(getAdapterPosition());
            if(listener != null){
                listener.onProductListSelected(product);
            }
        }
    }
}
