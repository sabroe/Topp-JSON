//jdeps --multi-release 11 --module-path lib --generate-module-info out Topp-JSON-Traversal-Library-0.5.0.jar
module com.yelstream.topp.json.traversal {
    requires static lombok;

    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;

    exports com.yelstream.topp.jackson.databind;
}
