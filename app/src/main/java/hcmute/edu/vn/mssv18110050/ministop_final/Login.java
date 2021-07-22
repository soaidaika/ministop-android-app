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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hcmute.edu.vn.mssv18110050.ministop_final.utils.HideSystemBars;

public class Login extends AppCompatActivity {
    private View decorView;
    private HideSystemBars hideSystemBar = new HideSystemBars();

    // EditText
    private EditText et_Email, et_Pass;

    // Button
    private Button bt_login;

    // Database
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        et_Email = findViewById(R.id.login_email);
        et_Pass = findViewById(R.id.login_pass);

        // Find view by Button id
        bt_login = findViewById(R.id.loginButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_Email.getText().toString();
                String pass = et_Pass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login successful! Redirecting...", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Please fill in your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(Login.this, Register.class);
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