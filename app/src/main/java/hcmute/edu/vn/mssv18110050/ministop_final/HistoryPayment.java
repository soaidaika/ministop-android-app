package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.HistoryPaymentAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Bill;

public class HistoryPayment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private BottomNavigationView bottomNavigationView;

    private List<Bill> bills;
    private RecyclerView list_of_bill;
    private HistoryPaymentAdapter historyPaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_history);
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
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        return true;
                    case R.id.nav_history:
                        return true;
                }
                return false;
            }
        });

        list_of_bill = findViewById(R.id.bill_list);
        bills = new ArrayList<>();
        historyPaymentAdapter = new HistoryPaymentAdapter(getApplicationContext(), bills);
        list_of_bill.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        list_of_bill.setAdapter(historyPaymentAdapter);

        db.collection("User").document(mAuth.getCurrentUser().getUid())
                .collection("Bill").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        for (DocumentChange doc : task.getResult().getDocumentChanges()) {
                            Bill bill = doc.getDocument().toObject(Bill.class);
                            bills.add(bill);
                        }
                        historyPaymentAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(HistoryPayment.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}