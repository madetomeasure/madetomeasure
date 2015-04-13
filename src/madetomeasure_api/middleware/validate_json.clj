(ns madetomeasure-api.middleware.validate-json
  (:require [clojure.java.io :as io]
            [cheshire.core :refer :all])
  (:import (com.github.fge.jsonschema.main JsonSchemaFactory JsonSchema)
           (com.fasterxml.jackson.databind ObjectMapper JsonMappingException)))

(def
  ^{:private true
    :doc "An immutable and therefore thread-safe JSON schema factory.
         You can call (.getJsonSchema json-schema-factory )
         to retrieve a JsonSchema instance which can validate JSON."}
  json-schema-factory
  (JsonSchemaFactory/byDefault))

(def
  ^{:private true
    :doc "Initialize the object mapper first and keep it private as not all
         of its methods are thread-safe. Optionally configure it here.
         Reader instances are cheap to create."}
  get-object-reader
  (let [object-mapper (ObjectMapper.)]
    (fn [] (.reader object-mapper))))

(defn- parse-to-node
  "Parse the given String as JSON. Returns a Jackson JsonNode."
  [data] (.readTree (get-object-reader) data))

(defn- get-schema
  "Get the schema file's contents in form of a string. Function only expects
  the schema name, i.e. 'collection' or 'image'."
  [schema-name]
  (println (str "raml_specs/schemas/" schema-name ".json"))
  (slurp (io/resource (str "madetomeasure_api/raml_specs/schemas/" schema-name ".json"))))

(defn- jsend-fail-response
  "This returns a fail response for the given validation"
  [schema-report parsed-data]
  (generate-string {:status "fail" :data (parse-string (str parsed-data)) :message (map (fn [r] (parse-string (str (.asJson r)))) schema-report)}))

(defn- jsend-error-response
  "This returns notification that the JSON is malformed somehow"
  [json-mapping-exception body]
  (generate-string {:status "error" :data (.read body) :message (.getMessage json-mapping-exception)}))


(defn- validate
  "Validates the given 'data' against the JSON schema. Returns an object
  with a :success property that equals true when the schema could
  be validated successfully. It additionally contains a :message property
  with a human readable error description."
  [schema-name data]
  (try
    (let [parsed-schema (parse-to-node (get-schema schema-name))
          schema (.getJsonSchema json-schema-factory parsed-schema)
          parsed-data (parse-to-node data)
          report (.validate schema parsed-data)]
      {:success (.isSuccess report)
       :message (jsend-fail-response report parsed-data)})
    (catch JsonMappingException e {:success false :message (jsend-error-response e data)})))

(defn- err-handler [validation]
  (fn [req]
    {:status 400 :headers {"Content-Type" "application/json"} :body (:message validation)}))

(defn json-schema-validate [schema-name]
  (fn [handler]
    (fn [request]
      (let [body (:body request)
            validation (validate schema-name body)
            valid? (:success validation)
            errback (err-handler validation)
            h (if (not valid?) errback handler)
            response (h request)]
        response))))
