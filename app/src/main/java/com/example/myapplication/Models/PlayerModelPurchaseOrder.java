package com.example.myapplication.Models;

import android.content.pm.ApplicationInfo;

public  class PlayerModelPurchaseOrder {

    public ApplicationInfo applicationInfo;
    private String product, quantity, dpprice,total,amount;
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getdpPrice() {
        return dpprice;
    }
    public void setdpPrice(String dpprice) {
        this.dpprice = dpprice;
    }
}
