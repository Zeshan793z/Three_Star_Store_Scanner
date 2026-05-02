package com.example.three_star_store_scanner.ui.billing;

public class BillItem {
    public String name;
    public String barcode;
    public double price;
    public int quantity;

    public BillItem(String name, String barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.quantity = 1; // default
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
