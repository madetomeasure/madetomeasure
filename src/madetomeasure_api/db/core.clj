(ns madetomeasure-api.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]))

(def db-spec
  {:subprotocol "postgresql"
   :subname (env :database-url)})

(defqueries "sql/queries.sql" {:connection db-spec})
