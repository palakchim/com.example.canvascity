//package com.example.canvascity.Fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.canvascity.R;
//import com.example.canvascity.adapter.CategoryAdapter;
//import com.example.canvascity.adapter.ProductAdapter;
//import com.example.canvascity.model.Category;
//import com.example.canvascity.model.Product;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WardrobeFragment extends Fragment {
//
//    RecyclerView rvCategories, rvProducts;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);
//
//        rvCategories = view.findViewById(R.id.rvCategories);
//        rvProducts = view.findViewById(R.id.rvProducts);
//
//        // Layout Managers
//        rvCategories.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
//        );
//
//        rvProducts.setLayoutManager(
//                new GridLayoutManager(getContext(), 2)
//        );
//
//        // ✅ CORRECT ADAPTER SETUP
//        CategoryAdapter categoryAdapter =
//                new CategoryAdapter(getCategories());
//        rvCategories.setAdapter(categoryAdapter);
//
//        rvProducts.setAdapter(
//                new ProductAdapter(getProducts())
//        );
//
//        return view;
//    }
//
//    // ---------------- DATA ----------------
//
//    private List<Category> getCategories() {
//        List<Category> list = new ArrayList<>();
//        list.add(new Category("Men", android.R.drawable.ic_menu_camera));
//        list.add(new Category("Women", android.R.drawable.ic_menu_compass));
//        list.add(new Category("Shoes", android.R.drawable.ic_menu_crop));
//        return list;
//    }
//
//    private List<Product> getProducts() {
//        List<Product> list = new ArrayList<>();
//        list.add(new Product("Hoodie", android.R.drawable.ic_menu_gallery, 2500));
//        list.add(new Product("Jacket", android.R.drawable.ic_menu_gallery, 3500));
//        list.add(new Product("Sneakers", android.R.drawable.ic_menu_gallery, 4200));
//        return list;
//    }
//}
