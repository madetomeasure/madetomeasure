(ns madetomeasure-api.routes.subscribers
  (:require [madetomeasure-api.layout :as layout]
            [madetomeasure-api.db.core :as db]
            [cheshire.core :refer :all]
            [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [madetomeasure-api.middleware.validate-json :refer [json-schema-validate]])
  (:import java.sql.SQLException))

(def default-subscriber-map
  ^{:private true}
  {:first_name nil :last_name nil})

(defn- subscriber-params [request]
  (:params request))

(defn- extract-subscriber [request]
  (merge default-subscriber-map (subscriber-params request)))

(defn- jsend-success-response [subscriber]
  (generate-string {:status "success" :data subscriber}))

(defn- jsend-error-response [subscriber message]
  {:status 406, :body (generate-string {:status "error" :data subscriber :message message})})

(defn get-subscribers []
  (str "Not implemented"))

(defn create-subscribers [request]
  (let [subscriber (extract-subscriber request)]
    (try
      (db/create-subscriber! subscriber)
      (jsend-success-response subscriber)
    (catch SQLException e 
      (let [pretty-print (fn [e] (.getMessage e))
            exceptions (map pretty-print e)]
        (jsend-error-response subscriber exceptions))))))

(defn get-subscriber [id]
  (str "Id: " id))

(defn delete-subscriber [id]
  (str "Id: " id ))

(defn update-subscriber [id]
  (str "Id: " id))

(defroutes validated-subscriber-routes*
           (POST "/subscribers" request (create-subscribers request))
           (PATCH ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id))
           (POST ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id)))

(defroutes unvalidated-subscriber-routes
           (GET "/subscribers" [] (get-subscribers))
           (GET ["/subscribers/:id" :id #"\d+"] [id] (get-subscriber id))
           (DELETE ["/subscribers/:id" :id #"\d+"] [id] (delete-subscriber id)))

(def validated-subscriber-routes
  (-> #'validated-subscriber-routes* ((json-schema-validate "subscriber"))))
