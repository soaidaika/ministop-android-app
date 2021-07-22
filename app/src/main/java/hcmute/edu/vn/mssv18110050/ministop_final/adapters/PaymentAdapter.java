package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private Context context;
    private Product p;
    private List<Product> products;

    // Constructor
    public PaymentAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {
       holder.pending_item_name.setText(products.get(position).getName());
       holder.pending_item_price.setText("Price:" + products.get(position).getPrice() + " VND");
       Glide.with(context).load(products.get(position).getImg_uri()).into(holder.pending_item_img);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pending_item_img;
        private TextView pending_item_name;
        private TextView pending_item_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pending_item_img = itemView.findViewById(R.id.pending_item_img);
            pending_item_name = itemView.findViewById(R.id.pendng_item_name);
            pending_item_price = itemView.findViewById(R.id.pending_item_price);
        }
    }
}
