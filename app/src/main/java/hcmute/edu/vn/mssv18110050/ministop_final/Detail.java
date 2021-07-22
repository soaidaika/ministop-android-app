package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class Detail extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private ImageView product_img;
    private TextView product_name;
    private TextView product_price;
    private TextView product_type;
    private TextView product_description;

    private Button addToCart_btn;
    private Button buyNow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Product product;
        product = (Product) getIntent().getSerializableExtra("detail");

        product_img = findViewById(R.id.product_detail_image);
        product_name = findViewById(R.id.product_detail_name);
        product_price = findViewById(R.id.product_detail_price);
        product_type = findViewById(R.id.product_detail_type);
        product_description = findViewById(R.id.product_detail_description);

        addToCart_btn = findViewById(R.id.add_to_cartButton);
        buyNow_btn = findViewById(R.id.buy_nowButton);

        // Load current product's detail
        Glide.with(getApplicationContext()).load(product.getImg_uri()).into(product_img);
        product_name.setText(product.getName());
        product_price.setText(product.getPrice() + " VND");
        product_type.setText(product.getType());
        product_description.setText(product.getDescription());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        addToCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("User").document(mAuth.getCurrentUser().getUid())
                        .collection("Cart").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Detail.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        buyNow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detail.this, AddressActivity.class);
                i.putExtra("item", product);
                startActivity(i);
            }
        });
    }
}