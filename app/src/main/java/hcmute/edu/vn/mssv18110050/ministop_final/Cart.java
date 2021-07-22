package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.CartAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class Cart extends AppCompatActivity implements CartAdapter.ProductRemoved {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private List<Product> productList;
    private RecyclerView cartRV;
    private CartAdapter cartAdapter;

    private Button cart_buy_btn;
    private TextView total_price;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), AllProducts.class));
                        return true;
                    case R.id.nav_cart:
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

        productList = new ArrayList<>();
        cartRV = findViewById(R.id.cart_recyclerview);
        cartRV.setLayoutManager(new LinearLayoutManager(this));
        cartRV.setHasFixedSize(true);

        total_price = findViewById(R.id.cart_total_price);

        cart_buy_btn = findViewById(R.id.cart_buyButton);
        cart_buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this, AddressActivity.class);
                i.putExtra("cart_list", (Serializable) productList);
                startActivity(i);
            }
        });

        cartAdapter = new CartAdapter(productList, this);
        cartRV.setAdapter(cartAdapter);

        db.collection("User").document(mAuth.getCurrentUser().getUid())
                .collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        for (DocumentChange doc : task.getResult().getDocumentChanges()) {
                            String document_id = doc.getDocument().getId();
                            Product product = doc.getDocument().toObject(Product.class);
                            product.setDocid(document_id);
                            productList.add(product);
                        }
                        calculate_total_price(productList);
                        cartAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(Cart.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void calculate_total_price(List<Product> productList) {
        int temp = 0;

        for (Product product : productList) {
            temp += product.getPrice();
        }

        total_price.setText(String.valueOf(temp));
    }

    @Override
    public void onProductRemoved(List<Product> productList) {
        calculate_total_price(productList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}