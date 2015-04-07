(ns madetomeasure-api.routes.subscribers
  (:require [madetomeasure-api.layout :as layout]
            [compojure.core :refer [defroutes GET POST DELETE PATCH]]))

(defn get-subscribers []
  (str "Not implemented"))

(defn create-subscribers []
  (str "Not implemented"))

(defn get-subscriber [id]
  (str "Id: " id))

(defn delete-subscriber [id]
  (str "Id: " id ))

(defn update-subscriber [id]
  (str "Id: " id))


(defroutes subscriber-routes
           (GET "/subscribers" [] (get-subscribers))
           (POST "/subscribers" [] (create-subscribers))
           (GET ["/subscribers/:id" :id #"\d+"] [id] (get-subscriber id))
           (DELETE ["/subscribers/:id" :id #"\d+"] [id] (delete-subscriber id))
           (PATCH ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id))
           (POST ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id)))
