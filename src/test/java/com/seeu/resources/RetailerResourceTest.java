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

    @Test
    public void testDelete() throws Exception {
        Response response = new RetailerResource().delete("7ea8a76b-8061-4457-92eb-4ae7665dc203");
        System.out.println(response.getEntity());
    }

    @Test
    public void testGetAll() throws Exception {
        Response response = new RetailerResource().getAll();
        System.out.println(response.getEntity());
    }
}