package com.seeu.resources;

import com.seeu.common.Responses;
import com.seeu.domains.Product;
import com.seeu.domains.ProductPrice;
import com.seeu.services.ProductPricingService;
import com.seeu.services.ProductPricingServiceImpl;
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
    ProductService productService = new ProductServiceImpl();
    ProductPricingService productPricingService = new ProductPricingServiceImpl();

    @GET
    public Response getAll() throws Exception {
        return Response.ok(toJson(productService.getAll())).build();
    }

    @GET
    @Path("/{product_id}")
    public Response get(@PathParam("product_id") String productId) throws Exception {
        if (productId == null || productId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        return Response.ok(toJson(productService.get(productId))).build();
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
        productService.save(product);
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @PUT
    @Path("/{product_id}/pricing")
    public Response savePricing(@PathParam("product_id") String productId, String payload) throws Exception {
        ProductPrice productPrice = fromJson(payload, ProductPrice.class);
        productPrice.setProductId(productId);
        if (productPrice.getId() == null || productPrice.getId().isEmpty()) {
            productPrice.setId(UUID.randomUUID().toString());
        }
        String validate = productPrice.validate();
        if (validate != null) {
            throw new BadRequestException(validate);
        }
        productPricingService.save(productPrice);
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @DELETE
    @Path("/{product_id}")
    public Response delete(@PathParam("product_id") String productId) throws Exception {
        if (productId == null || productId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        productService.delete(productId);
        return Response.ok(Responses.DELETE_SUCCESSFUL).build();
    }

    @DELETE
    @Path("/{product_id}/pricing/{product_pricing_id}")
    public Response deletePricing(@PathParam("product_id") String productId,
                                  @PathParam("product_pricing_id") String productPricingId) throws Exception {
        if (productId == null || productId.isEmpty() || productPricingId == null || productPricingId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        productPricingService.delete(productId, productPricingId);
        return Response.ok(Responses.DELETE_SUCCESSFUL).build();
    }
}
