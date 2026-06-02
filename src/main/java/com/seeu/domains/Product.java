package com.seeu.domains;

import com.google.gson.annotations.SerializedName;

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

    @Override
    public String validate() {
        String validation = isValidId(getId());
        if (validation != null) {
            return validation;
        }
        return null;
    }
}
