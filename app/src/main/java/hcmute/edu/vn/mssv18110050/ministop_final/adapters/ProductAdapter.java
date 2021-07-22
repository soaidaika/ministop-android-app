package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.Detail;
import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context context;
    private final List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.product_price.setText("Price: " + productList.get(position).getPrice() + " VND");
        holder.product_name.setText(productList.get(position).getName());
        Glide.with(context).load(productList.get(position).getImg_uri()).into(holder.product_img);

        // When click the image, go to DetailActivity
        Product product_detail = productList.get(position);
        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Detail.class);
                i.putExtra("detail", product_detail);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView product_img;
        private final TextView product_name;
        private final TextView product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.productThumbnail);
            product_name = itemView.findViewById(R.id.productTitle);
            product_price = itemView.findViewById(R.id.productPrice);
        }
    }
}

