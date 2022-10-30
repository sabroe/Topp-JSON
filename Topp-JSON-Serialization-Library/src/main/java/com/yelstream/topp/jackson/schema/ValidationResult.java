package com.yelstream.topp.jackson.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * Result of JSON document validation against a JSON Schema.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
@AllArgsConstructor
public class ValidationResult {
    /**
     * JSON tree node evaluated.
     */
    private final JsonNode node;

    /**
     * Validation messages.
     */
    private final Set<ValidationMessage> validationMessages;

    /**
     * Indicates, if the validation is successful.
     * @return Indicates, if the validation is successful.
     */
    public boolean isValid() {
        return validationMessages == null || validationMessages.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString()+"//"+(validationMessages == null?"":ValidationMessages.toString(validationMessages));
    }
}
