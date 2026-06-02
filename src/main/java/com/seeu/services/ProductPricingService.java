package com.seeu.services;

import com.seeu.domains.ProductPrice;

import java.util.List;
import java.util.Map;

public interface ProductPricingService {
    List<ProductPrice> getByProductId(String productId) throws Exception;

    Map<String, List<ProductPrice>> getAllGroupedByProduct() throws Exception;

    void save(ProductPrice productPrice) throws Exception;

    void delete(String productId, String productPricingId) throws Exception;
}
