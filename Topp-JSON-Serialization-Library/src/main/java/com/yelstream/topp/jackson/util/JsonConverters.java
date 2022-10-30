package com.yelstream.topp.jackson.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.yelstream.topp.jackson.schema.JsonSchemaType;
import com.yelstream.topp.jackson.schema.JsonSchemas;
import lombok.experimental.UtilityClass;

import java.net.URI;

/**
 * Utility addressing instances of {@link JsonConverter}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class JsonConverters {
    /**
     * Creates a converter between textual JSON documents and Jackson POJOs.
     * @param schema JSON Schema constraint.
     * @param clazz Jackson object class for the deserialized form of JSON documents.
     * @param objectMapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param <T> Type of Jackson object for the deserialized form of JSON documents.
     * @return Converter.
     */
    public static <T> JsonConverter<T> createJsonConverter(JsonSchema schema,
                                                           Class<T> clazz,
                                                           ObjectMapper objectMapper) {
        JsonSchemaType<T> schemaType=new JsonSchemaType<>(schema,clazz);
        return new JsonConverter<>(schemaType,objectMapper);
    }

    /**
     * Creates a converter between textual JSON documents and Jackson POJOs.
     * @param jsonSchemaResourceName JSON Schema resource name.
     * @param clazz Jackson object class for the deserialized form of JSON documents.
     * @param objectMapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param <T> Type of Jackson object for the deserialized form of JSON documents.
     * @return Converter.
     */
    public static <T> JsonConverter<T> createJsonConverter(String jsonSchemaResourceName,
                                                           Class<T> clazz,
                                                           ObjectMapper objectMapper) {
        JsonSchema schema=JsonSchemas.getSchema(jsonSchemaResourceName);
        return createJsonConverter(schema,clazz,objectMapper);
    }

    /**
     * Creates a converter between textual JSON documents and Jackson POJOs.
     * @param jsonSchemaResource JSON Schema resource.
     * @param clazz Jackson object class for the deserialized form of JSON documents.
     * @param objectMapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param <T> Type of Jackson object for the deserialized form of JSON documents.
     * @return Converter.
     */
    public static <T> JsonConverter<T> createJsonConverter(URI jsonSchemaResource,
                                                           Class<T> clazz,
                                                           ObjectMapper objectMapper) {
        JsonSchema schema=JsonSchemas.getSchema(jsonSchemaResource);
        return createJsonConverter(schema,clazz,objectMapper);
    }
}
