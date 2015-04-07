(ns madetomeasure-api.routes.campaigns
  (:require [madetomeasure-api.layout :as layout]
            [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [clojure.java.io :as io]))

(defn get-campaigns []
  (str "Not Implemented")
  {:status 501})

(defn show-campaign [id]
  (str "ID: " id))

(defn create-campaigns []
  (str "Not Implemented")
  {:status 501})

(defn destroy-campaign [id]
  {:status 501})

(defn update-campaign [id]
  {:status 501})

(defn get-campaign-subscribers [id]
  {:status 501})

(defn delete-campaign-subscriber [id subscriber-id]
  {:status 501})

(defn subscribe-to-campaign [id]
  {:status 501})


(defroutes campaign-routes
           (GET "/campaigns" [] (get-campaigns))
           (POST "/campaigns" [] (create-campaigns))
           (GET ["/campaigns/:id" :id #"\d+"] [id] (show-campaign id))
           (POST ["/campaigns/:id" :id #"\d+"] [id] (update-campaign id))
           (PATCH ["/campaigns/:id" :id #"\d+"] [id] (update-campaign id))
           (GET ["/campaigns/:id/subscribers" :id #"\d+"] [id] (get-campaign-subscribers id))
           (POST ["/campaigns/:id/subscribers" :id #"\d+"] [id] (subscribe-to-campaign id))
           (DELETE ["/campaigns/:id/subscribers/:subscriber-id" :id #"\d+" :subscriber-id #"\d+"] [id subscriber-id] (delete-campaign-subscriber id subscriber-id)))
