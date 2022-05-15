package com.yelstream.topp.jackson.schema;

import lombok.Getter;

import java.io.IOException;

/**
 * Indicates a failed verification of a JSON document against its JSON Schema.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
public final class ValidationException extends IOException {
    /**
     * Result of JSON document validation against a JSON Schema.
     */
    private final ValidationResult validationResult;

    /**
     * Constructor.
     * @param validationResult Result of JSON document validation.
     */
    public ValidationException(ValidationResult validationResult) {
        super();
        this.validationResult=validationResult;
    }

    /**
     * Constructor.
     * @param message Detail message.
     * @param validationResult Result of JSON document validation.
     */
    public ValidationException(String message,final ValidationResult validationResult) {
        super(message);
        this.validationResult=validationResult;
    }
}
