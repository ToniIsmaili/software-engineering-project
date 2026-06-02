package com.seeu.resources;

import com.seeu.BaseTest;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

public class ScraperResourceTest extends BaseTest {

    @Test
    public void testGet() throws Exception {
        String retailerId = "6b5bad3f-eda3-4362-b529-6201f635ec03";
        Response response = new ScraperResource().get(retailerId);
        System.out.println(response.getEntity());
    }

    @Test
    public void testSave() throws Exception {
        String payload = """
                {
                    "status": "IDLE"
                }
                """;
        String retailerId = "6b5bad3f-eda3-4362-b529-6201f635ec03";
        Response response = new ScraperResource().save(retailerId, payload);
        System.out.println(response.getEntity());
    }

    @Test
    public void testDelete() throws Exception {
        String retailerId = "6b5bad3f-eda3-4362-b529-6201f635ec03";
        Response response = new ScraperResource().delete(retailerId);
        System.out.println(response.getEntity());
    }
}