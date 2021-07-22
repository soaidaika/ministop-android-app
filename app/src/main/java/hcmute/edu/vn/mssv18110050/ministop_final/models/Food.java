package hcmute.edu.vn.mssv18110050.ministop_final.models;

public class Food {
    String food_type;
    String img_uri;

    public Food() { }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String type) {
        this.food_type = type;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_url) {
        this.img_uri = img_url;
    }
}