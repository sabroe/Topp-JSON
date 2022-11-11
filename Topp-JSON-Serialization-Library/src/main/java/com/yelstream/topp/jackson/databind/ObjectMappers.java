package com.yelstream.topp.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * Utility addressing instances of {@link ObjectMapper}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class ObjectMappers {
    /**
     * Converts a Jackson POJO to a JSON string.
     * This uses the default writer as specified by the mapper.
     * @param mapper Jackson object mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String toString(ObjectMapper mapper,
                                  Object value) {
        String text;
        ObjectWriter writer=mapper.writer();
        try {
            text=writer.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(String.format("Failure to write Jackson object as text; mapper is %s, Jackson object is %s!",mapper.getClass().getName(),value),ex);
        }
        return text;
    }

    /**
     * Converts a Jackson POJO to a JSON string.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the default writer as specified by the mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String toString(Object value) {
        ObjectMapper mapper=new ObjectMapper();
        return toString(mapper,value);
    }

    /**
     * Converts a Jackson POJO to a JSON string.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param mapper Jackson object mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String toStringWithPrettyPrint(ObjectMapper mapper,
                                                 Object value) {
        String text;
        ObjectWriter writer=mapper.writerWithDefaultPrettyPrinter();
        try {
            text=writer.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(String.format("Failure to write Jackson object as text; mapper is %s, Jackson object is %s!",mapper.getClass().getName(),value),ex);
        }
        return text;
    }

    /**
     * Converts a Jackson POJO to a JSON string.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String toStringWithPrettyPrint(Object value) {
        ObjectMapper mapper=new ObjectMapper();
        return toStringWithPrettyPrint(mapper,value);
    }

    /**
     * Formats a JSON string.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the default writer as specified by the mapper.
     * @param mapper Jackson object mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String format(ObjectMapper mapper,
                                String value) {
        String text;
        try {
            Object objectValue=mapper.readValue(value,Object.class);
            text=toString(mapper,objectValue);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(String.format("Failure to format JSON text; mapper is %s, text is %s!",mapper.getClass().getName(),value),ex);
        }
        return text;
    }

    /**
     * Formats a JSON string.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the default writer as specified by the mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String format(String value) {
        ObjectMapper mapper=new ObjectMapper();
        return format(mapper,value);
    }

    /**
     * Formats a JSON string.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param mapper Jackson object mapper.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String formatWithPrettyPrint(ObjectMapper mapper,
                                               String value) {
        String text;
        try {
            Object objectValue=mapper.readValue(value,Object.class);
            text=toStringWithPrettyPrint(mapper,objectValue);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(String.format("Failure to format JSON text; mapper is %s, text is %s!",mapper.getClass().getName(),value),ex);
        }
        return text;
    }

    /**
     * Formats a JSON string.
     * This uses a default {@link ObjectMapper} with no special handling of types or modules added.
     * This uses the specified mapper with the default "pretty print"-writer activated.
     * @param value Jackson POJO.
     * @return JSON string.
     */
    public static String formatWithPrettyPrint(String value) {
        ObjectMapper mapper=new ObjectMapper();
        return formatWithPrettyPrint(mapper,value);
    }

    /**
     * Deserializes a JSON document into a Jackson POJO.
     * @param <T> Type of Jackson POJO.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param document JSON document.
     * @param type Type of Jackson POJO.
     * @return Jackson POJO.
     * @throws IOException Thrown in case of errors.
     */
    public static <T> T deserialize(ObjectMapper mapper,
                                    String document,
                                    Class<T> type) throws IOException {
        T value;
        try {
            value=mapper.readValue(document,type);
        } catch (JsonProcessingException ex) {
            throw new IOException(String.format("Failure to deserialize JSON text to Jackson object; mapper is %s, JSON text is %s, type is %s!",mapper.getClass().getName(),document,type.getName()),ex);
        }
        return value;
    }

    /**
     * Serializes a Jackson POJO into a JSON document.
     * @param mapper Configuration of mapping between JSON documents and Jackson POJOs.
     * @param value Jackson POJO.
     * @return JSON document.
     * @throws IOException Thrown in case of errors.
     */
    public static String serialize(ObjectMapper mapper,
                                   Object value) throws IOException {
        String document;
        ObjectWriter writer=mapper.writer();
        try {
            document=writer.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IOException(String.format("Failure to serialize Jackson object to JSON text; mapper is %s, Jackson object is %s!",mapper.getClass().getName(),value),ex);
        }
        return document;
    }
}
