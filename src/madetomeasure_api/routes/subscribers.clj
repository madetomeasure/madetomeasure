(ns madetomeasure-api.routes.subscribers
  (:require [madetomeasure-api.layout :as layout]
            [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [madetomeasure-api.middleware.validate-json :refer [json-schema-validate]]))

(defn get-subscribers []
  (str "Not implemented"))

(defn create-subscribers []
  {:status 200 :headers {"Content-Type" "application/json"}})

(defn get-subscriber [id]
  (str "Id: " id))

(defn delete-subscriber [id]
  (str "Id: " id ))

(defn update-subscriber [id]
  (str "Id: " id))

(defroutes validated-subscriber-routes*
           (POST "/subscribers" [] (create-subscribers))
           (PATCH ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id))
           (POST ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id)))

(defroutes unvalidated-subscriber-routes*
           (GET "/subscribers" [] (get-subscribers))
           (GET ["/subscribers/:id" :id #"\d+"] [id] (get-subscriber id))
           (DELETE ["/subscribers/:id" :id #"\d+"] [id] (delete-subscriber id)))

(def subscriber-routes
  (-> #'validated-subscriber-routes* ((json-schema-validate "subscriber"))))
      
