(ns madetomeasure-api.middleware.validate-json
  (:require [clojure.java.io :as io]
            [cheshire.core :refer :all]
            [madetomeasure-api.middleware.present-response :as present-response :refer :all])
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
  (slurp (io/resource (str "madetomeasure_api/raml_specs/schemas/" schema-name ".json"))))

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
      {:success (.isSuccess report) :error false :message (map (fn [r] (parse-string (str (.asJson r)))) report)})
    (catch JsonMappingException e {:success false :error true :message (.getMessage e)})))

(defn- err-handler [validation data]
  (fn [req]
    (let [response  (if (:error validation)
                      (present-response/error data (:message validation))
                      (present-response/fail data (:message validation)))]
    {:status 400 :headers {"Content-Type" "application/json"} :body response})))

(defn json-schema-validate [handler schema-name]
  (fn [request]
    (let [body (:body request)
          validation (validate schema-name body)
          valid? (:success validation)
          data (:params request)
          errback (err-handler validation data)
          h (if (not valid?) errback handler)
          response (h request)]
      response)))
