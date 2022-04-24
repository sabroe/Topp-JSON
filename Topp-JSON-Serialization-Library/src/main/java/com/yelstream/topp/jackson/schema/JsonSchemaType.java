package com.yelstream.topp.jackson.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;

/**
 * Association of a JSON Schema and a deserialized object type.
 * This is immutable.
 * @param <T> Type of Jackson object for the deserialized form of JSON documents.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class JsonSchemaType<T> {
    /**
     * JSON Schema constraint.
     */
    private final JsonSchema schema;

    /**
     * Type of deserialized form of JSON text.
     */
    private final Class<T> type;

    /**
     * Validates a JSON document against the JSON Schema.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @return Result of validation.
     * @throws IOException Thrown in case of errors.
     */
    public ValidationResult validate(ObjectMapper mapper,
                                     String document) throws IOException {
        return JsonSchemas.validate(schema,mapper,document);
    }

    /**
     * Verifies a JSON document against the JSON Schema.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public void verify(ObjectMapper mapper,
                       String document) throws IOException {
        JsonSchemas.verify(schema,mapper,document);
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     */
    public T deserialize(ObjectMapper mapper,
                         String document) throws IOException {
        return JsonSchemas.deserialize(schema,mapper,document,type);
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @param verify Indicates, if verification of JSON document against its JSON Schema is to be performed.
     *               If the JSON Schema is not present then this has no effect.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public T deserialize(ObjectMapper mapper,
                         String document,
                         boolean verify) throws IOException {
        return JsonSchemas.deserialize(verify?schema:null,mapper,document,type);
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * If the schema is non-{@code null} then the JSON document is verified after serialization.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param value Jackson POJO.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public String serialize(ObjectMapper mapper,
                            Object value) throws IOException {
        return JsonSchemas.serialize(schema,mapper,value);
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param value Jackson POJO.
     * @param verify Indicates, if verification of JSON document against its JSON Schema is to be performed.
     *               If the JSON Schema is not present then this has no effect.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public String serialize(ObjectMapper mapper,
                            Object value,
                            boolean verify) throws IOException {
        return JsonSchemas.serialize(verify?schema:null,mapper,value);
    }
}
