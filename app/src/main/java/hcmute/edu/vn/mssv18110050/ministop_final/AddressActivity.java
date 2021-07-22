package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.AddressAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Address;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class AddressActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Button payment_btn;
    private Button add_address_btn;
    private RecyclerView address_rv;
    private List<Address> addressList;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        final Object obj = getIntent().getSerializableExtra("item");
        List<Product> products = (ArrayList<Product>) getIntent().getSerializableExtra("cart_list");
//        Toast.makeText(this, products.get(0).getName(), Toast.LENGTH_SHORT).show();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        address_rv = findViewById(R.id.address_recyclerView);
        add_address_btn = findViewById(R.id.add_paymentButton);
        payment_btn = findViewById(R.id.checkoutButton);

        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressList);
        address_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        address_rv.setAdapter(addressAdapter);

        db.collection("User").document(mAuth.getCurrentUser().getUid())
                .collection("Address").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc:task.getResult()) {
                                Address address = doc.toObject(Address.class);
                                addressList.add(address);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddressActivity.this, AddAddress.class);

                startActivity(i);
            }
        });

        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 0;
                Product p = null;
                if (obj instanceof Product) {
                    p = (Product) obj;
                }

                if (products != null && products.size() > 0) { // If buy more than 1 product
                    Intent i = new Intent(AddressActivity.this, Payment.class);
                    i.putExtra("cart_list", (Serializable) products);
                    startActivity(i);
                } else { // If buy only 1 product
                    Intent i = new Intent(AddressActivity.this, Payment.class);
                    i.putExtra("cost", p);
                    startActivity(i);
                }
            }
        });
    }
}