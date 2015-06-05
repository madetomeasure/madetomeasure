(ns madetomeasure-api.routes.subscribers
  (:require [madetomeasure-api.layout :as layout]
            [madetomeasure-api.db.core :as db]
            [cheshire.core :refer :all]
            [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [madetomeasure-api.middleware.present-response :refer :all]
            [madetomeasure-api.middleware.validate-json :refer [json-schema-validate]]))

(def default-subscriber-map
  ^{:private true}
  {:first_name nil :last_name nil})

(defn- subscriber-params [request]
  (let [params (:params request)
        body (:body request)]
    (.reset body)
    (if (empty? params)
                         (parse-string (slurp body))
                         [params])))

(defn- extract-subscribers [request]
  (let [subscribers (subscriber-params request)]
    (map (fn [m] merge default-subscriber-map m) subscribers)))

(defn get-subscribers []
  (str "Not implemented"))

(defn create-subscribers [request]
  (execute
    request
    (let [subscribers (extract-subscribers request)
          insertions (apply clojure.java.jdbc/insert! db/db-spec :subscribers subscribers)]
      (success insertions))))

(defn get-subscriber [id]
  (str "Id: " id))

(defn delete-subscriber [str-id]
  (let [id (Integer/parseInt str-id)
        rows-deleted (db/delete-subscriber! {:id id})]
    (if (= 0 rows-deleted)
      {:status 404, :body (fail {:id str-id} (str "Subscriber not found with id: " str-id))}
      (success {:id str-id}))))

(defn update-subscriber [id]
  (str "Id: " id))

(defroutes validated-subscriber-routes
           (POST "/subscribers" _ 
                 (json-schema-validate 
                   (fn [req] (create-subscribers req))
                   "subscriber"
                   ))
           (PATCH ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id))
           (POST ["/subscribers/:id" :id #"\d+"] [id] (update-subscriber id)))

(defroutes unvalidated-subscriber-routes
           (GET "/subscribers" [] (get-subscribers))
           (GET ["/subscribers/:id" :id #"\d+"] [id] (get-subscriber id))
           (DELETE ["/subscribers/:id" :id #"\d+"] [id] (delete-subscriber id)))
