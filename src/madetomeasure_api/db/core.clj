(ns madetomeasure-api.db.core
  (:require
    [yesql.core :refer [defqueries]]))

(def db-spec
  {:subprotocol "postgresql"
   :subname "//localhost/madetomeasure_development?user=matthewkirk"
   :user "madetomeasure_development"})

(defqueries "sql/queries.sql" {:connection db-spec})
