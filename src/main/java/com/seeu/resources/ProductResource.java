package com.seeu.resources;

import com.seeu.domains.Product;
import com.seeu.services.ProductService;
import com.seeu.services.ProductServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/products")
public class ProductResource extends BaseResource {
    ProductService service = new ProductServiceImpl();

    @GET
    public Response getAll() throws Exception {
        return Response.ok(toJson(service.getAll())).build();
    }

    @GET
    @Path("/{product_id}")
    public Response get(@PathParam("product_id") String productId) throws Exception {
        if (productId == null || productId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        return Response.ok(toJson(service.get(productId))).build();
    }

    @PUT
    public Response save(String payload) throws Exception {
        Product product = fromJson(payload, Product.class);
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(UUID.randomUUID().toString());
        }
        String validate = product.validate();
        if (validate != null) {
            throw new BadRequestException(validate);
        }
        service.save(product);
        return Response.ok("Product Saved Successfully!").build();
    }

    @DELETE
    @Path("/{product_id}")
    public Response delete(@PathParam("product_id") String productId) throws Exception {
        if (productId == null || productId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        service.delete(productId);
        return Response.ok("Deleted successfully!").build();
    }
}
