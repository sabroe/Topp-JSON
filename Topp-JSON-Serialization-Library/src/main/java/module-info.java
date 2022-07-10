//jdeps --multi-release 11 --module-path lib --generate-module-info out Topp-JSON-Serialization-Library-0.5.0.jar
module com.yelstream.topp.json.serialization {
    requires static lombok;

    requires com.fasterxml.jackson.core;
    requires org.slf4j;

    requires transitive com.fasterxml.jackson.databind;
    requires transitive json.schema.validator;

    exports com.yelstream.topp.jackson.databind;
    exports com.yelstream.topp.jackson.schema;
    exports com.yelstream.topp.jackson.util;
}
