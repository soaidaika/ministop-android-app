package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.mssv18110050.ministop_final.utils.HideSystemBars;

public class Register extends AppCompatActivity {
    private View decorView;
    private HideSystemBars hideSystemBar = new HideSystemBars();

    // EditText
    private EditText et_Name, et_Email, et_Password;

    // Button
    private Button bt_reg;

    // Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar.HideSystemBars());
                }
            }
        });

        // Find view by EditText id
        et_Name = findViewById(R.id.reg_name);
        et_Email = findViewById(R.id.reg_email);
        et_Password = findViewById(R.id.reg_pass);

        // Find view by Button id
        bt_reg = findViewById(R.id.registerButton);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_Name.getText().toString();
                String email = et_Email.getText().toString();
                String passwd = et_Password.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    et_Name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    et_Email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwd)) {
                    et_Password.setError("Password is required");
                    return;
                }
                if (passwd.length() < 6) {
                    et_Password.setError("Password must be more than 6 characters");
                    return;
                }

                if (!name.isEmpty() && !email.isEmpty() && !passwd.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("User").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("fName", name);
                                user.put("email", email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Register.this, "Your account is successfully registered!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent i = new Intent(Register.this, Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Register.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void goToSignin(View view) {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBar.HideSystemBars());
        }
    }

    public void BackButtonPressed(View view) {
        onBackPressed();
    }
}