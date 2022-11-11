package com.yelstream.topp.jackson.schema;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.urn.URNFactory;

import java.net.URI;

/**
 * Utility addressing instances of {@link JsonSchemaFactory}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-23
 */
public class JsonSchemaFactories {
    private JsonSchemaFactories() { }

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

    static class XXXURNFactory implements URNFactory {

        @Override
        public URI create(String urn) {
            System.out.println("URN: "+urn);
            return null;
        }
    }

    /**
     * XXX.
     */
    public static JsonSchemaFactory createSchemaFactory2() {
        JsonSchemaFactory schemaFactory=JsonSchemaFactory.getInstance(SCHEMA_VERSION);
        JsonSchemaFactory.Builder schemaFactoryBuilder=JsonSchemaFactory.builder(schemaFactory);

        schemaFactoryBuilder.addUrnFactory(new XXXURNFactory());

        return schemaFactoryBuilder.build();
    }

    public static JsonSchema getSchema2(URI resource) {
        JsonSchemaFactory jsonSchemaFactory=createSchemaFactory2();
        return jsonSchemaFactory.getSchema(resource);
    }
}
