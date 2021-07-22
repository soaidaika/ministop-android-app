package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Product> productList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    ProductRemoved productRemoved;

    // Constructor
    public CartAdapter(List<Product> productList, ProductRemoved productRemoved) {
        this.productList = productList;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        this.productRemoved = productRemoved;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.product_name.setText(productList.get(position).getName());
        holder.product_price.setText(productList.get(position).getPrice() + " VND");
        Glide.with(holder.itemView.getContext()).load(productList.get(position).getImg_uri()).into(holder.product_img);
        holder.remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("User").document(mAuth.getCurrentUser().getUid())
                        .collection("Cart").document(productList.get(position).getDocid())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            productList.remove(productList.get(position));
                            notifyDataSetChanged();
                            productRemoved.onProductRemoved(productList);
                            Toast.makeText(holder.itemView.getContext(), "Product removed from cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(holder.itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView product_img;
        private TextView product_name;
        private TextView product_price;
        private TextView remove_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.cart_item_img);
            product_name = itemView.findViewById(R.id.cart_item_name);
            product_price = itemView.findViewById(R.id.cart_item_price);
            remove_product = itemView.findViewById(R.id.cart_remove_product);
        }
    }

    public interface ProductRemoved {
        public void onProductRemoved(List<Product> productList);
    }
}

