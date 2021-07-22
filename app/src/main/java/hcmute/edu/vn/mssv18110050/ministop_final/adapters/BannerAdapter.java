package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.R;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private Context context;
    private List<String> uris;

    // Constructor
    public BannerAdapter (Context context, List<String> uris) {
        this.context = context;
        this.uris = uris;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        String uri = uris.get(position % uris.size());
        Glide.with(context).load(uri).into(holder.banner);
    }

    @Override
    public int getItemCount() {
        return uris == null ? 0 : Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_image);
        }
    }
}
