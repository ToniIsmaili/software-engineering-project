package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class ProductPrice extends BaseEntity {
    @SerializedName("price")
    private Integer price;
    @SerializedName("availability")
    private Boolean availability;
    @SerializedName("last_updated")
    private Instant lastUpdated;
    @SerializedName("product")
    private Product product;
    @SerializedName("retailer")
    private Retailer retailer;

    public ProductPrice() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        return null;
    }
}
