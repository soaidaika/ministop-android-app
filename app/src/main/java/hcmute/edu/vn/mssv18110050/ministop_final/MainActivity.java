package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import hcmute.edu.vn.mssv18110050.ministop_final.utils.HideSystemBars;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private View decorView;
    private HideSystemBars hideSystemBar = new HideSystemBars();
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar.HideSystemBars());
                }
            }
        });

    }

    // When the user clicks on Login button, redirect to Login screen
    public void proceedToLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    // When the user clicks on Register button, redirect to Register screen
    public void proceedToRegister(View view) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    public void BackButtonPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        counter++;
        if(counter > 1){
            System.exit(0);
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        final long DELAY_TIME = 3000L;
        new Thread(new Runnable() {
            public void run(){
                try {
                    Thread.sleep(DELAY_TIME);
                    counter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Stay-logged-in check on start-up
    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is already logged in but still hasn't logged out
        // If the user is logged in, redirect to HomeActivity
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBar.HideSystemBars());
        }
    }
}