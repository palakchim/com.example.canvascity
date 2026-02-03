//package com.example.canvascity.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.canvascity.R;
//import com.example.canvascity.model.Product;
//
//import java.util.List;
//
//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
//
//    List<Product> list;
//
//    public ProductAdapter(List<Product> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_product, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Product product = list.get(position);
//        holder.name.setText(product.getName());
//        holder.price.setText("₹ " + product.getPrice());
//        holder.image.setImageResource(product.getImage());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView name, price;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.ivProduct);
//            name = itemView.findViewById(R.id.tvProductName);
//            price = itemView.findViewById(R.id.tvProductPrice);
//        }
//    }
//}
