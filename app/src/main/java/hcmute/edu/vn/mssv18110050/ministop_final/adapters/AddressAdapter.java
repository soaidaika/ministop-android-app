package hcmute.edu.vn.mssv18110050.ministop_final.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.mssv18110050.ministop_final.R;
import hcmute.edu.vn.mssv18110050.ministop_final.models.Address;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context applicationContext;
    private List<Address> addressList;
    private RadioButton selectedRadioButton;

    // Constructor
    public AddressAdapter(Context applicationContext, List<Address> addressList) {
        this.applicationContext = applicationContext;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        holder.fulladdress.setText(addressList.get(position).getAddress());
        holder.selected_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set unchecked all other elements in the list, so to display only one selected radio button at a time
                for (Address address:addressList) {
                    address.setSelected(false);
                }

                // Set "checked" the model associated to the clicked radio button
                addressList.get(position).setSelected(true);

                // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
                if (selectedRadioButton != null && !v.equals(selectedRadioButton)) {
                    selectedRadioButton.setChecked(false);
                }

                // Replace the previous selected radio button with the current (clicked) one, and "check" it
                selectedRadioButton = (RadioButton) v;
                selectedRadioButton.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fulladdress;
        private final RadioButton selected_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fulladdress = itemView.findViewById(R.id.full_address);
            selected_address = itemView.findViewById(R.id.select_address);
        }
    }
}
