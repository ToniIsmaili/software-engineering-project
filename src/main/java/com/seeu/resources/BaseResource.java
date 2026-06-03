package com.seeu.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.seeu.common.Responses;
import com.seeu.common.Utils;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BaseResource {
    public <T> T fromJson(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }

    public <T> String toJson(T t) {
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.toString())
                )
                .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, typeOfT, context) ->
                        Instant.parse(json.getAsString())
                )
                .create()
                .toJson(t);
    }

    public void validateIds(String... ids) throws Exception {
        for (String id : ids) {
            if (Utils.isNullOrEmpty(id)) {
                throw new BadRequestException(Responses.MISSING_ID);
            }
            try {
                UUID.fromString(id);
            } catch (Exception ignored) {
                throw new BadRequestException(Responses.INVALID_ID);
            }
        }
    }
}
