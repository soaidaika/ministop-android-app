package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder> {

    private Context context;
    private List<Product> purchased_products;

    // Constructor
    public HistoryDetailAdapter(Context context, List<Product> purchased_products) {
        this.context = context;
        this.purchased_products = purchased_products;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryDetailAdapter.ViewHolder holder, int position) {
        holder.item_name.setText(purchased_products.get(position).getName());
        holder.item_price.setText("Price: " + purchased_products.get(position).getPrice() + " VND");
        Glide.with(context).load(purchased_products.get(position).getImg_uri()).into(holder.item_img);
    }

    @Override
    public int getItemCount() {
        return purchased_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_img;
        private TextView item_name;
        private TextView item_price;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
        }
    }
}
