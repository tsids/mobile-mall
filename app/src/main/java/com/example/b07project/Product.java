package com.example.b07project;

import java.io.Serializable;

public class Product implements Serializable {
    private String imageURL;
    private String title;
    private float price;
    private String description;
    private int storeID;
    private int productID;
    private String key;

    public Product() {}

    public Product(String imageURL, String title, float price, String description, int storeID, int productID) {
        this.imageURL = imageURL;
        this.title = title;
        this.price = price;
        this.description = description;
        this.storeID = storeID;
        this.productID = productID;

    }

    public String getImageURL() { return imageURL; }

    public String getTitle() { return title; }

    public float getPrice() { return price; }

    public String getDescription() { return description; }

    public int getStoreID() { return storeID; }

    public int getProductID() { return productID; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public void setTitle(String title) { this.title = title; }

    public void setPrice(float price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setStoreID(int storeID) { this.storeID = storeID; }

    public void setProductID(int productID) { this.productID = productID; }

    @Override
    public int hashCode() { return (storeID * 13 + productID * 31); }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (!(obj instanceof Product)) { return false; }
        Product otherProd = (Product) obj;
        return ((this.productID == otherProd.productID) && (this.storeID == otherProd.storeID));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
