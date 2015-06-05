(ns madetomeasure-api.middleware.present-response
  (:require [cheshire.core :refer :all])
  (:import java.sql.SQLException))

(defn- compact-map
  "This will compact the map by trimming all nil values"
  [map]
  (select-keys map (for [[k v] map :when (not (nil? v))] k)))

(defn fail 
  "There was a problem with the data submitted, or some pre-condition of the API call wasn't satisfied Required params of data and message"
  [data message]
  (let [base-response {:status "fail" :message message :data data}
        response (compact-map base-response)]

  (generate-string response)))

(defn error 
  "An error occurred in processing the request, i.e. an exception was thrown
   Requires message optional code and data"
  [data message & code]
  (let [base-response {:status "error" :message message :code code :data data}
        response (compact-map base-response)]
  (generate-string response)))

(defn success
  "All went well, and (usually) some data was returned."
  [data]
  (generate-string {:status "success" :data data}))

(defmacro execute
  "Convenience macro for catching errors and fails"
  [request execution]
  (let [e (gensym 'e)
        pretty-print (fn [ex] (.getMessage ex))
        exceptions (gensym 'exceptions)]
  `(try
     ~execution
   (catch SQLException ~e
     (let [~exceptions (map ~pretty-print ~e)]
       (fail ~exceptions (:params ~request)))))))
