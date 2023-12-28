package org.jsanchez.recipe.application.types.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Generic Serializer for application enum objects
 * @param <T> an enum object
 */
public class ApplicationEnumTypeSerializer<T extends Enum<T>> implements JsonSerializer<T> {
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
