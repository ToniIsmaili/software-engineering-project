package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
    @SerializedName("retailer_id")
    private String retailerId;
    private transient String productId;

    public ProductPrice() {
    }

    public ProductPrice(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setPrice(rs.getInt("price"));
        setAvailability(rs.getBoolean("availability"));
        setLastUpdated(rs.getTimestamp("last_updated").toInstant());
        setProductId(rs.getString("product_id"));
        setRetailerId(rs.getString("retailer_id"));
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

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
        validation = isValidId(getProductId());
        if (validation != null) {
            return validation;
        }
        validation = isValidId(getRetailerId());
        if (validation != null) {
            return validation;
        }
        return null;
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        ps.setString(1, getId());
        ps.setInt(2, getPrice());
        ps.setBoolean(3, getAvailability());
        ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        ps.setString(5, getProductId());
        ps.setString(6, getRetailerId());
    }
}
