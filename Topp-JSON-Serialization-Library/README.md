# JSON Converter Library

Module: `json-converter-library`

## Description

Utility addressing the context of JSON and Jackson - in particular JSON document formatting and JSON document validation relative to JSON schemas:

* Object `ObjectMappers` addresses basic serialization and formatting.
* Object `JsonSchemas` handles validation and serialization relativt to JSON schemas.
* Object `JsonSchemaType` associates JSON schema information with Jackson POJO types.
* High-level conversion between JSON documents and Jackson POJOs is handled by `JsonConverter`.

Note that:

* Validation exists in both a controlled and an uncontrolled forms.
  
  The controlled form returns a validation result in the form of `ValidationMessages` - see `JsonSchemas#validate()`.
  The uncontrolled form is a hard verification throwing an exception in case of validation errors - see `JsonSchemas#verify()`.

* Validation relative to JSON schema is independent of serialization and deserialization.

  This means that the operations can be called and activated independently.

  The `JSONSchemas` object does per default try to validate JSON documents both before deserialization and after serialization -
if an actual schema is specified.

For further specifics, see the Java Documentation!

## Dependencies

Dependencies are restricted! This module is of common interest and must not introduce libraries out of context.

Dependencies are Jackson libraries only.
This includes network-nt schema-validation as and addition to core Jackson APIs.
