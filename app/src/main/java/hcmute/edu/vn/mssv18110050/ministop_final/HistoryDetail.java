package hcmute.edu.vn.mssv18110050.ministop_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.adapters.HistoryDetailAdapter;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Bill;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class HistoryDetail extends AppCompatActivity {

    private RecyclerView purchased_product_rv;
    private HistoryDetailAdapter adapter;

    private TextView subtotal;
    private TextView discount;
    private TextView discounted_price;
    private TextView shipping_price;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Bill bill;
        bill = (Bill) getIntent().getSerializableExtra("purchase_detail");

        purchased_product_rv = findViewById(R.id.purchased_product_rv);
        purchased_product_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new HistoryDetailAdapter(getApplicationContext(), bill.getProducts());
        purchased_product_rv.setAdapter(adapter);

        subtotal = findViewById(R.id.purchased_subtotal_cost);
        discount = findViewById(R.id.purchased_discount_percentage);
        discounted_price = findViewById(R.id.purchased_price_after_discount);
        shipping_price = findViewById(R.id.purchased_shipping_cost);
        total = findViewById(R.id.purchased_total_cost);

        subtotal.setText(String.valueOf(bill.getSubtotal()));
        discount.setText(String.valueOf(bill.getDiscount()));
        discounted_price.setText(String.valueOf(bill.getDiscounted_price()));
        shipping_price.setText(String.valueOf(bill.getShipping_price()));
        total.setText(String.valueOf(bill.getFinal_price()));

    }
}