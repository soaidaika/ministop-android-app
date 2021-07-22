package hcmute.edu.vn.mssv18110050.ministop_final.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.AllProducts;
import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.adapters.FoodAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.adapters.MiscellaneousAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.adapters.ProductAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Food;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Miscellaneous;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    private TextView all_product;

    // Food tab
    private List<Food> foodList;
    private FoodAdapter foodAdapter;
    private RecyclerView foodCategoriesRecycleView;

    // Miscellaneous tab
    private List<Miscellaneous> miscellaneousList;
    private MiscellaneousAdapter miscellaneousAdapter;
    private RecyclerView miscellaneousCategoriesRecyclerView;

    // All products tab
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private RecyclerView productRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        all_product = view.findViewById(R.id.seeall3);

        // For food categories
        foodList = new ArrayList<>();
        foodCategoriesRecycleView = view.findViewById(R.id.food_category_recyclerview);
        foodAdapter = new FoodAdapter(getContext(), foodList);
        foodCategoriesRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        foodCategoriesRecycleView.setAdapter(foodAdapter);

        // For miscellaneous categories
        miscellaneousList = new ArrayList<>();
        miscellaneousCategoriesRecyclerView = view.findViewById(R.id.misc_category_recyclerview);
        miscellaneousAdapter = new MiscellaneousAdapter(getContext(), miscellaneousList);
        miscellaneousCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        miscellaneousCategoriesRecyclerView.setAdapter(miscellaneousAdapter);

        // For all products
        productList = new ArrayList<>();
        productRecyclerView = view.findViewById(R.id.all_products_recyclerview);
        productAdapter = new ProductAdapter(getContext(), productList);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        productRecyclerView.setAdapter(productAdapter);

        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Food food = document.toObject(Food.class);
                                foodList.add(food);
                                foodAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("Miscellaneous")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Miscellaneous miscellaneous = document.toObject(Miscellaneous.class);
                                miscellaneousList.add(miscellaneous);
                                miscellaneousAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("Items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        all_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AllProducts.class);
                startActivity(i);
            }
        });

        return view;
    }
}