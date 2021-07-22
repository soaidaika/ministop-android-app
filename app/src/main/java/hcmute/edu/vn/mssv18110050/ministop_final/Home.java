package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.BannerAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.fragment.HomeFragment;

public class Home extends AppCompatActivity {

    private Fragment homeFragment;
    private RecyclerView rvBanner;
    private List<String> uris;
    private BannerAdapter bannerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private BottomNavigationView bottomNavigationView;

    private Timer timer;
    private TimerTask timerTask;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeFragment = new HomeFragment();
        load_fragment(homeFragment);

        // Initialize and assign variables
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        getBannerImgUriList();
        rvBanner = findViewById(R.id.rv_banner);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvBanner.setLayoutManager(linearLayoutManager);
        bannerAdapter = new BannerAdapter(this, uris);
        rvBanner.setAdapter(bannerAdapter);

        // Perform ItemSelectedListener for bottom nav bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
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
                        startActivity(new Intent(getApplicationContext(), HistoryPayment.class));
                        return true;
                }
                return false;
            }
        });

        // Get middle position
        // In this case, 2147483647 / 2
        // This is needed because if starts at position 0, the banner cannot scroll to the left
        if (uris != null) {
            position = Integer.MAX_VALUE / 2;
            rvBanner.scrollToPosition(position);
        }

        // Auto align banner to middle position when scroll
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvBanner);
        rvBanner.smoothScrollBy(5, 0);

        // Get scroll event of the banner
        // Prevent the banner auto scroll back to its last initial position
        rvBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 1) {
                    stopBannerAutoScroll();
                } else if (newState == 0) {
                    position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    runBannerAutoScroll();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        runBannerAutoScroll();
    }

    // When the user tap out to background, stopping the banner from auto scrolling
    @Override
    protected void onPause() {
        super.onPause();
        stopBannerAutoScroll();
    }

    private void stopBannerAutoScroll() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timerTask = null;
            timer.cancel();
            timer = null;
            position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();    // Save position
        }
    }

    private void runBannerAutoScroll() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        rvBanner.scrollToPosition(position);
                        rvBanner.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        rvBanner.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    private void getBannerImgUriList() {
        uris = new ArrayList<>();
        uris.add("https://www.ministop.vn/img/campaign/60c9b0d798f94_e974eec020b0ced5dc24519362ea5794.jpg");
        uris.add("https://www.ministop.vn/img/campaign/60c9b188ed87b_3f84d5de944ef85c97fdd3ee7ec6fcbb.jpg");
        uris.add("https://www.ministop.vn/img/campaign/60c9b2591e757_47dd3b570346ec675d19813e0739563d.jpg");
        uris.add("https://www.ministop.vn/img/campaign/60cabdd4f09aa_d7e2e23b658e1f0ba4cc710ab85e9cf9.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5d5f4ad8ef224_b858c40259e50d1f0d29d303f47f8050.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5e0eac07804ee_14198322dda2b9ea5a1c6579b42462e3.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5e96b3e3ee897_7cf000b2530427ae5193207893b3796b.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5eb2367569e86_f0db945ea0daf568f53e4010c4b857ee.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5ef2be891aab7_c8949837bee0466f05fab8e2bded3673.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5ef2bed9d1968_dc60532e2ff2f01cbe14cfbee05d62d7.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5f4e214883f27_240095085dbb4aab7f03fb6c86be93b0.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5f58a4c15aa66_18ea30fa449722cb8d799c2d9855a8ab.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5f6ac1dd9c6e6_44a04d0c67161db45af140cbe61d6cda.jpg");
        uris.add("https://www.ministop.vn/img/campaign/5f6af72eeff2b_14c0878439e746df91e9b5e7475e3e3f.jpg");
    }

    private void load_fragment(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.home_layout, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }
}