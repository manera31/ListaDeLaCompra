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
    private List list;

    public AdapterProductList(ArrayList<Product> products, IProductListListener listener, Context context){
        this.products = products;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
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

    public void setList(List list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private ImageView ivImage;
        private CheckBox cbName;
        private IProductListListener listener;
        private Context context;

        public ProductViewHolder(View view, IProductListListener listener, Context context){
            super(view);
            this.listener = listener;
            this.context = context;
            ivImage = view.findViewById(R.id.ivProductImage);
            cbName = view.findViewById(R.id.cbProductName);
            cbName.setOnCheckedChangeListener(this);
        }

        public void bindCategory(int position){
            Product product = products.get(position);
            ivImage.setImageResource(product.getImage());
            cbName.setText(product.getName());

            for (Product p: list.getProducts()){
                if (product.getName().toLowerCase().equals(p.getName().toLowerCase()) && !cbName.isChecked()){
                    cbName.setChecked(true);
                }
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                listener.onProductListSelected(products.get(getAdapterPosition()), true);
            } else {
                listener.onProductListSelected(products.get(getAdapterPosition()), false);
            }
        }
    }
}
