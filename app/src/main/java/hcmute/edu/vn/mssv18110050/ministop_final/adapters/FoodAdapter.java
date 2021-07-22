package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.AllProducts;
import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Food;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_categories, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        // Load image
        Glide.with(context).load(foodList.get(position).getImg_uri()).into(holder.food_type_image);

        // Click on each food category to open menu of each type of food
        holder.food_type_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AllProducts.class);
                i.putExtra("type", foodList.get(position).getFood_type());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView food_type_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            food_type_image = itemView.findViewById(R.id.food_category_image);
        }
    }
}

