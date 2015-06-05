(ns madetomeasure-api.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [clojure.string :as str]
    [environ.core :refer [env]]))

(def db-spec
  (env :database-url))

(defqueries "sql/queries.sql" {:connection db-spec})
