package hcmute.edu.vn.mssv18110050.ministop_final.models;

public class Address {
    String Address;
    boolean current_selected;

    public boolean isSelected() {
        return current_selected;
    }

    public void setSelected(boolean selected) {
        current_selected = selected;
    }

    public Address() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
}
