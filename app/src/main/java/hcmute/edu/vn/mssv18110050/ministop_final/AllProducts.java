package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.AllProductsAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

import static android.content.ContentValues.TAG;

public class AllProducts extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<Product> products;
    private RecyclerView productRV;
    private AllProductsAdapter productsAdapter;
    private BottomNavigationView bottomNavigationView;

    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        // Get intent from
        String type = getIntent().getStringExtra("type");

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        return true;
                    case R.id.nav_search:
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        return true;
                    case R.id.nav_history:
                        startActivity(new Intent(getApplicationContext(), HistoryPayment.class));
                        return true;
                }
                return false;
            }
        });

        //region INITIALIZERS
        db = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
        productRV = findViewById(R.id.all_products);
        productRV.setLayoutManager(new GridLayoutManager(this, 1));
        productsAdapter = new AllProductsAdapter(this, products);
        productRV.setAdapter(productsAdapter);
        //endregion

        search = findViewById(R.id.search_text_bar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        //region FOOD CATEGORIES
        if (type == null || type.isEmpty()) {
            db.collection("Items")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Sandwiches")) {
            db.collection("Items").whereEqualTo("type", "Sandwiches")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Desserts")) {
            db.collection("Items").whereEqualTo("type", "Desserts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Lunch boxes")) {
            db.collection("Items").whereEqualTo("type", "Lunch boxes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Noodles")) {
            db.collection("Items").whereEqualTo("type", "Noodles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Oden")) {
            db.collection("Items").whereEqualTo("type", "Oden")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Onigiri")) {
            db.collection("Items").whereEqualTo("type", "Onigiri")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Salad")) {
            db.collection("Items").whereEqualTo("type", "Salad")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Drinks")) {
            db.collection("Items").whereEqualTo("type", "Drinks")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Street foods")) {
            db.collection("Items").whereEqualTo("type", "Street foods")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        //endregion

        // region MISCELLANEOUS CATEGORIES
        if (type != null && type.equalsIgnoreCase("Alcohol")) {
            db.collection("Items").whereEqualTo("type", "Alcohol")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Cosmetics")) {
            db.collection("Items").whereEqualTo("type", "Cosmetics")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Daily goods")) {
            db.collection("Items").whereEqualTo("type", "Daily goods")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("Stationery")) {
            db.collection("Items").whereEqualTo("type", "Stationery")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    products.add(product);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        //endregion
    }

    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (Product product : products) {
            if (product.getName().toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(product);
            }
        }

        productsAdapter.filterList(filteredList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}