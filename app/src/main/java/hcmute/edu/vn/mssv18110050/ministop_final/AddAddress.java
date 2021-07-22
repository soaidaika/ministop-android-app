package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddress extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private EditText a_contact_name;
    private EditText a_street_address;
    private EditText a_city;
    private EditText a_phone;

    private Button addAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        a_contact_name = findViewById(R.id.address_contactname);
        a_street_address = findViewById(R.id.address_street);
        a_city = findViewById(R.id.address_city);
        a_phone = findViewById(R.id.address_phone);

        addAddressButton = findViewById(R.id.add_paymentButton);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = a_contact_name.getText().toString();
                String streetAddress = a_street_address.getText().toString();
                String city = a_city.getText().toString();
                String phone = a_phone.getText().toString();

                String full_address_contact = "";
                if (!contactName.isEmpty()) {
                    full_address_contact += contactName + "\n";
                }
                if (!streetAddress.isEmpty()) {
                    full_address_contact += streetAddress + ", ";
                }
                if (!city.isEmpty()) {
                    full_address_contact += city + "\n";
                }
                if (!phone.isEmpty()) {
                    full_address_contact += phone;
                }

                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("Address", full_address_contact);

                db.collection("User").document(mAuth.getCurrentUser().getUid())
                        .collection("Address").add(stringMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddAddress.this, "Address added successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(new Intent(getApplicationContext(), AddressActivity.class));
                                    overridePendingTransition(0, 0);
                                }
                            }
                        });
            }
        });

    }
}