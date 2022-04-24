package com.yelstream.topp.jackson.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.yelstream.topp.jackson.databind.ObjectMappers;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

/**
 * Utility addressing instances of {@link JsonSchema}.
 */
@Slf4j
@UtilityClass
public class JsonSchemas {
    /**
     * Default IETF JSON Schema version.
     */
    public static final SpecVersion.VersionFlag SCHEMA_VERSION=SpecVersion.VersionFlag.V7;

    /**
     * Creates a JSON-schema factory with default settings.
     * @return JSON-schema factory.
     */
    public static JsonSchemaFactory createSchemaFactory() {
        return JsonSchemaFactory.getInstance(SCHEMA_VERSION);
    }

    /**
     * Gets the JSON-schema for a specific resource.
     * @param resource Resource.
     * @return JSON-schema.
     */
    public static JsonSchema getSchema(URI resource) {
        JsonSchemaFactory jsonSchemaFactory=createSchemaFactory();
        return jsonSchemaFactory.getSchema(resource);
    }

    /**
     * Gets the JSON-schema for a specific resource.
     * @param resourceName Resource name.
     * @return JSON-schema.
     */
    public static JsonSchema getSchema(String resourceName) {
        URI resource=URI.create(resourceName);
        return getSchema(resource);
    }

    /**
     * Validates a JSON documents against JSON-schema.
     * The result of the validation is returned in a controlled manner as the return argument.
     * @param schema IETF JSON Schema.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @return Validation result.
     * @throws IOException Thrown in case of errors.
     */
    public static ValidationResult validate(JsonSchema schema,
                                            ObjectMapper mapper,
                                            String document) throws IOException {
        JsonNode node=mapper.readTree(document);
        Set<ValidationMessage> validationMessages=schema.validate(node);
        return new ValidationResult(node,validationMessages);
    }

    /**
     * Verifies a JSON documents against a JSON Schema.
     * If verification is negative then an exception is thrown.
     * @param schema IETF JSON Schema.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public static void verify(JsonSchema schema,
                              ObjectMapper mapper,
                              String document) throws IOException {
        ValidationResult validationResult=validate(schema,mapper,document);
        if (!validationResult.isValid()) {
            throw new ValidationException(String.format("Failure to verify format of JSON; JSON text is %s, validation messages are %s!",document,ValidationMessages.toString(validationResult.getValidationMessages())),validationResult);
        }
    }

    /**
     * Conditionally verifies a JSON documents against a JSON Schema.
     * The verification if and only if the schema is non-{@code null}.
     * If verification is negative then an exception is thrown.
     * @param schema IETF JSON Schema.
     *               This may be {@code null}.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @return Indicates, if verification has been performed.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")  //This is nonsense! MSM, 2022-02-10.
    private static boolean conditionalVerify(JsonSchema schema,
                                             ObjectMapper mapper,
                                             String document) throws IOException {
        boolean result=false;
        if (schema == null) {
            log.trace("Ignoring validation of document since schema is not set; mapper is {}, JSON text is {}.",mapper.getClass().getName(),document);
        } else {
            verify(schema,mapper,document);
            result=true;
        }
        return result;
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * If the schema given is non-{@code null} then the JSON document is verified before deserialization.
     * @param schema IETF JSON Schema.
     *               This may be {@code null}.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @param type Type of Jackson POJO.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     * @param <T> Type of Jackson POJO.
     */
    public static <T> T deserialize(JsonSchema schema,
                                    ObjectMapper mapper,
                                    String document,
                                    Class<T> type) throws IOException {
        if (!conditionalVerify(schema,mapper,document)) {
            log.debug("Ignoring validation of document before deserialization since schema is not set; type is {}.",type.getName());
        }
        return ObjectMappers.deserialize(mapper,document,type);
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * If the schema given is non-{@code null} then the JSON document is verified after serialization.
     * @param schema IETF JSON Schema.
     *               This may be {@code null}.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param value Jackson POJO.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     * @throws ValidationException Thrown in case of validation errors.
     */
    public static String serialize(JsonSchema schema,
                                   ObjectMapper mapper,
                                   Object value) throws IOException {
        String document=ObjectMappers.serialize(mapper,value);
        if (!conditionalVerify(schema,mapper,document)) {
            log.debug("Ignoring validation of document after serialization since schema is not set; type is {}.",value.getClass().getName());
        }
        return document;
    }
}
