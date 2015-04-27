(ns madetomeasure-api.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [clojure.string :as str]
    [environ.core :refer [env]]))


(def db-spec
  "For some reason jdbc doesn't have a function to parse this url type
  So this takes the databse-url and returns a given map that can be used
  by Yesql:
  JDBC takes the form of jdbc:vendor//host/dbname?user=name"
  (let [connection-url (or (System/getenv "DATABASE_URL")
                           (env :database-url))
        parts (str/split connection-url #":")
        subprotocol (nth parts 1)
        subname (nth parts 2)]
    {:subprotocol subprotocol :subname subname}))

(defqueries "sql/queries.sql" {:connection db-spec})
