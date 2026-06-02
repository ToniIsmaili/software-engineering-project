package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Product extends BaseEntity {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("brand")
    private String brand;
    @SerializedName("category")
    private String category;
    @SerializedName("specifications")
    private String specifications;
    @SerializedName("product_prices")
    private List<ProductPrice> productPrices;

    public Product() {
    }

    public Product(ResultSet rs) throws Exception {
        setId(rs.getString("id"));
        setName(rs.getString("name"));
        setDescription(rs.getString("description"));
        setBrand(rs.getString("brand"));
        setCategory(rs.getString("category"));
        setSpecifications(rs.getString("specifications"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        ps.setString(1, getId());
        ps.setString(2, getName());
        ps.setString(3, getDescription());
        ps.setString(4, getBrand());
        ps.setString(5, getCategory());
        ps.setString(6, getSpecifications());
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
