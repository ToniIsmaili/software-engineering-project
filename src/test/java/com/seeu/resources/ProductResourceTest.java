package com.seeu.resources;

import com.seeu.BaseTest;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

public class ProductResourceTest extends BaseTest {

    @Test
    public void testGetAll() throws Exception {
        System.out.println(new ProductResource().getAll().getEntity());
    }

    @Test
    public void testGet() throws Exception {
        String productId = "5932e2a1-7db6-4774-aee8-9b43639f8e21";
        Response response = new ProductResource().get(productId);
        System.out.println(response.getEntity());
    }

    @Test
    public void testSave() throws Exception {
        String payload = """
                {
                  "id": "5932e2a1-7db6-4774-aee8-9b43639f8e21",
                  "name": "Wireless Bluetooth Headphones",
                  "description": "Over-ear wireless headphones with noise cancellation and 30-hour battery life.",
                  "brand": "SoundCore",
                  "category": "Electronics",
                  "specifications": "500G easy to use blah blah blah updated"
                }
                """;
        Response response = new ProductResource().save(payload);
        System.out.println(response.getEntity());
    }

    @Test
    public void testSavePricing() throws Exception {
        String payload = """
                {
                  "price": 12999,
                  "availability": true,
                  "retailer_id": "6b5bad3f-eda3-4362-b529-6201f635ec03"
                }
                """;
        Response response = new ProductResource().savePricing("5932e2a1-7db6-4774-aee8-9b43639f8e21", payload);
        System.out.println(response.getEntity());
    }

    @Test
    public void testDelete() throws Exception {
        String productId = "5932e2a1-7db6-4774-aee8-9b43639f8e21";
        Response response = new ProductResource().delete(productId);
        System.out.println(response.getEntity());
    }

    @Test
    public void testDeletePricing() throws Exception {
        String productId = "5932e2a1-7db6-4774-aee8-9b43639f8e21";
        String pricingId = "77570258-ace7-4e51-8edd-944921a1e499";
        Response response = new ProductResource().deletePricing(productId, pricingId);
        System.out.println(response.getEntity());
    }
}