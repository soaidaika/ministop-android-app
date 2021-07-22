package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.PaymentAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Bill;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class Payment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private PaymentAdapter paymentAdapter;
    private RecyclerView pending_products;
    private List<Product> products;

    private TextView subtotal_cost;
    private TextView discount_percentage;
    private TextView discounted_price;
    private TextView shipping_cost;
    private TextView total_cost;

    private Button checkout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        subtotal_cost = findViewById(R.id.subtotal_cost);
        discount_percentage = findViewById(R.id.discount_percentage);
        discounted_price = findViewById(R.id.price_after_discount);
        shipping_cost = findViewById(R.id.shipping_cost);
        total_cost = findViewById(R.id.total_cost);
        checkout_btn = findViewById(R.id.checkoutButton);

        products = (ArrayList<Product>) getIntent().getSerializableExtra("cart_list");
        Product p = (Product) getIntent().getSerializableExtra("cost");

        pending_products = findViewById(R.id.pending_product_rv);

        int subtotal = 0;

        if (products != null && products.size() > 0) {
            for (Product product : products) {
                subtotal += product.getPrice();
            }
            subtotal_cost.setText(String.valueOf(subtotal));
            paymentAdapter = new PaymentAdapter(getApplicationContext(), products);
        } else {
            products = new ArrayList<>();
            products.add(p);
            subtotal = p.getPrice();
            subtotal_cost.setText(String.valueOf(subtotal));
            paymentAdapter = new PaymentAdapter(getApplicationContext(), products);
        }

        pending_products.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        pending_products.setAdapter(paymentAdapter);

        // Generate a random discount percentage :>
        double random = Math.random();
        double discount = random * 100;
        double subtotal_after_discount = subtotal - (subtotal * random);

        // Shipping cost is also random :>
        int shipping = (int)(50000 * random);

        int total = (int)(subtotal_after_discount + shipping);

        discount_percentage.setText(String.valueOf((int)discount));
        discounted_price.setText((String.valueOf((int)subtotal_after_discount)));
        shipping_cost.setText(String.valueOf(shipping));
        total_cost.setText(String.valueOf(total));

        int finalSubtotal = subtotal;
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            final Bill bill = new Bill();
            @Override
            public void onClick(View v) {
                bill.setProducts(products);
                bill.setDate(new Date());
                bill.setSubtotal(finalSubtotal);
                bill.setDiscount((int)discount);
                bill.setDiscounted_price((int)subtotal_after_discount);
                bill.setShipping_price(shipping);
                bill.setFinal_price(total);

                Intent i = new Intent(Payment.this, Complete.class);
                i.putExtra("bill", bill);
                startActivity(i);
            }
        });
    }
}