package com.seeu.resources;

import com.seeu.BaseTest;
import jakarta.ws.rs.core.Response;
import org.junit.Test;

public class UserResourceTest extends BaseTest {

    @Test
    public void testGet() throws Exception {
        String userId = "688d23dd-2e07-44c2-90ad-8f8d4e98e555";
        Response response = new UserResource().get(userId);
        System.out.println(response.getEntity());
    }

    @Test
    public void testSave() throws Exception {
        String payload = """
                {
                    "email": "blerton@teset.com",
                    "password_hash": "123123123",
                    "is_verified": true,
                    "role": "ADMIN"
                }
                """;
        Response response = new UserResource().save(payload);
        System.out.println(response.getEntity());
    }

    @Test
    public void testDelete() throws Exception {
        String userId = "688d23dd-2e07-44c2-90ad-8f8d4e98e555";
        Response response = new UserResource().delete(userId);
        System.out.println(response.getEntity());
    }
}