package com.yelstream.topp.jackson.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelstream.topp.jackson.databind.ObjectMappers;
import com.yelstream.topp.jackson.schema.JsonSchemaType;
import com.yelstream.topp.jackson.schema.ValidationException;
import com.yelstream.topp.jackson.schema.ValidationResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;

/**
 * Converts between textual JSON documents and Jackson POJOs using a specific JSON schema and a specific Jackson object mapper.
 * @param <T> Type of Jackson POJOs for the deserialized form of textual JSON documents.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class JsonConverter<T> {
    /**
     * JSON Schema and its deserialized object type.
     */
    private final JsonSchemaType<T> schemaType;

    /**
     * Configuration of mapping between JSON documents and Jackson POJOs.
     */
    private final ObjectMapper mapper;

    /**
     * Validates a JSON document against the JSON Schema.
     * @param document JSON document.
     * @return Result of validation.
     * @throws IOException Thrown in case of errors.
     */
    public ValidationResult validate(String document) throws IOException {
        return schemaType.validate(mapper,document);
    }

    /**
     * Verifies a JSON document against the JSON Schema.
     * @param document JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public void verify(String document) throws IOException {
        schemaType.verify(mapper,document);
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * @param document JSON document.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     */
    public T deserialize(String document) throws IOException {
        return schemaType.deserialize(mapper,document);
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * @param document JSON document.
     * @param verify Indicates, if verification of JSON document against its JSON Schema is to be performed.
     *               If the JSON Schema is not present then this has no effect.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public T deserialize(String document,
                         boolean verify) throws IOException {
        return schemaType.deserialize(mapper,document,verify);
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * If the schema is non-{@code null} then the JSON document is verified after serialization.
     * @param value Jackson POJO.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public String serialize(Object value) throws IOException {
        return schemaType.serialize(mapper,value);
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * @param value Jackson POJO.
     * @param verify Indicates, if verification of JSON document against its JSON Schema is to be performed.
     *               If the JSON Schema is not present then this has no effect.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public String serialize(Object value,
                            boolean verify) throws IOException {
        return schemaType.serialize(mapper,value,verify);
    }

    /**
     * Converts a Jackson POJO to a JSON document.
     * This uses the default writer as specified by the mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public String toString(T value) {
        return ObjectMappers.toString(mapper,value);
    }

    /**
     * Converts a Jackson POJO to a JSON document.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public String toStringWithPrettyPrint(T value) {
        return ObjectMappers.toStringWithPrettyPrint(mapper,value);
    }

    /**
     * Formats a JSON document.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the default writer as specified by the mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public String format(String value) {
        return ObjectMappers.format(mapper,value);
    }

    /**
     * Formats a JSON document.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public String formatWithPrettyPrint(String value) {
        return ObjectMappers.formatWithPrettyPrint(mapper,value);
    }
}
