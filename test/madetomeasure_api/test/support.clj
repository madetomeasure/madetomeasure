(ns madetomeasure-api.test.support
  (:use ring.mock.request))

(defn json-post [endpoint body-string]
  (-> 
    (request :post endpoint)
    (body body-string)
    (content-type "application/json")))

(defn json-delete [endpoint]
  (->
    (request :delete endpoint)
    (content-type "application/json")))


