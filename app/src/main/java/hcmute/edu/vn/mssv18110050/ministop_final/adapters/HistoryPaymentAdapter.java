package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.HistoryDetail;
import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Bill;

public class HistoryPaymentAdapter extends RecyclerView.Adapter<HistoryPaymentAdapter.ViewHolder> {

    private Context context;
    private List<Bill> billList;

    // Constructor
    public HistoryPaymentAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryPaymentAdapter.ViewHolder holder, int position) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        holder.purchase_time.setText(sdf.format(billList.get(position).getDate()));
        holder.total_price.setText("Total: " + billList.get(position).getFinal_price() + " VND");

        Bill bill = billList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HistoryDetail.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("purchase_detail", (Serializable) bill);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView purchase_time;
        private TextView total_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            purchase_time = itemView.findViewById(R.id.purchase_time);
            total_price = itemView.findViewById(R.id.total_price);
        }
    }
}
