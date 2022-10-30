package com.yelstream.topp.jackson.schema;

import com.networknt.schema.ValidationMessage;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility addressing instances of {@link ValidationMessage}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class ValidationMessages {
    /**
     * Converts a set of validation messages to a textual representation.
     * @param validationMessages Validation messages.
     * @return Textual representation of validation messages.
     */
    public static String toString(Set<ValidationMessage> validationMessages) {
        return validationMessages.stream().map(ValidationMessage::getMessage).collect(Collectors.joining(System.lineSeparator()));
    }
}
