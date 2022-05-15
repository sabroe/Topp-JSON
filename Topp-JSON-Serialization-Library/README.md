# Topp JSON Serialization Library

Module: `topp-json-serialization-utility`

## Description

Topp JSON Serialization is an open-source Java library which implements utilities related to the serialization of JSON.

## Content

Utility addressing the context of JSON and Jackson - in particular JSON document formatting and JSON document validation relative to JSON schemas:

* Object [`ObjectMappers`](src/main/java/com/yelstream/topp/jackson/databind/ObjectMappers.java) addresses basic serialization and formatting.
* Object [`JsonSchemas`](src/main/java/com/yelstream/topp/jackson/schema/JsonSchemaFactories.java) handles validation and serialization relativt to JSON schemas.
* Object [`JsonSchemaType`](src/main/java/com/yelstream/topp/jackson/schema/JsonSchemaType.java) associates JSON schema information with Jackson POJO types.
* High-level conversion between JSON documents and Jackson POJOs is handled by [`JsonConverter`](src/main/java/com/yelstream/topp/jackson/util/JsonConverter.java).

Note that:

* Validation exists in both a controlled and an uncontrolled forms.

  The controlled form returns a validation result in the form of [`ValidationMessages`](src/main/java/com/yelstream/topp/jackson/schema/ValidationMessages.java) - see [`JsonSchemas#validate()`](src/main/java/com/yelstream/topp/jackson/schema/JsonSchemas.java).
  The uncontrolled form is a hard verification throwing an exception in case of validation errors - see [`JsonSchemas#verify()`](src/main/java/com/yelstream/topp/jackson/schema/JsonSchemas.java).

* Validation relative to JSON schema is independent of serialization and deserialization.

  This means that the operations can be called and activated independently.

  The [`JSONSchemas`](src/main/java/com/yelstream/topp/jackson/schema/JsonSchemas.java) object does per default try to validate JSON documents both before deserialization and after serialization -
  if an actual schema is specified.

For further specifics, see the Java Documentation!


## Artifacts

Artifacts are released to the [Maven Central Repository](https://search.maven.org/).

For the latest version,
search
[_Group ID_ `io.github.sabroe.topp` and _Artifact ID_ `topp-json-serialization-utility`](https://search.maven.org/search?q=g:io.github.sabroe.topp%20AND%20a:topp-json-serialization-utility).

---

_Greetings to all, Morten Sabroe Mortensen_
