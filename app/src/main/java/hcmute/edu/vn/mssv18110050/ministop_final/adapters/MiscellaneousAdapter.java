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
import hcmute.edu.vn.mssv18110050.ministop_final.models.Miscellaneous;

public class MiscellaneousAdapter extends RecyclerView.Adapter<MiscellaneousAdapter.ViewHolder> {
    private Context context;
    private List<Miscellaneous> miscellaneousList;

    public MiscellaneousAdapter(Context context, List<Miscellaneous> miscellaneousList) {
        this.context = context;
        this.miscellaneousList = miscellaneousList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.misc_categories, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiscellaneousAdapter.ViewHolder holder, int position) {
        // Load image
        Glide.with(context).load(miscellaneousList.get(position).getImg_uri()).into(holder.misc_type_image);

        // Click on each misc category to see the menu of each type of misc
        holder.misc_type_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AllProducts.class);
                i.putExtra("type", miscellaneousList.get(position).getMisc_type());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return miscellaneousList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView misc_type_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            misc_type_image = itemView.findViewById(R.id.misc_category_image);
        }
    }
}

