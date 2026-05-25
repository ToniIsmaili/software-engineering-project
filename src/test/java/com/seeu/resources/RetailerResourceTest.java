package com.seeu.resources;

import com.seeu.BaseTest;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

public class RetailerResourceTest extends BaseTest {
    @Test
    public void testSave() throws Exception {
        String payload = """
                {
                    "name": "Setec",
                    "url": "SomeURLLink"
                }
                """;
        Response response = new RetailerResource().save(payload);
        System.out.println(response.getEntity());
    }
}