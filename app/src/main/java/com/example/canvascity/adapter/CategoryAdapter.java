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
//import com.example.canvascity.model.Category;
//
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//    List<Category> list;
//
//    public CategoryAdapter(List<Category> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_category, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Category category = list.get(position);
//        holder.name.setText(category.getName());
//        holder.image.setImageResource(category.getImage());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView name;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.ivCategory);
//            name = itemView.findViewById(R.id.tvCategoryName);
//        }
//    }
//}
