package hcmute.edu.vn.mssv18110050.ministop_final.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {
    List<Product> products;
    Date date;
    int subtotal;
    int discount;
    int discounted_price;
    int shipping_price;
    int final_price;

    public Bill() { }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(int discounted_price) {
        this.discounted_price = discounted_price;
    }

    public int getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(int shipping_price) {
        this.shipping_price = shipping_price;
    }

    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int final_price) {
        this.final_price = final_price;
    }
}
