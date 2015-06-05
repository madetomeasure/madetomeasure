(ns madetomeasure-api.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [clojure.string :as str]
    [environ.core :refer [env]]))

; NOTE (cmhobbs) this function takes in a postgres adaptor URI in the
;      form of:  jdbc:postgresql://hostname/dbname?user=username&password=pass
;      and maps it to the standard format documented here:
;      http://clojure-doc.org/articles/ecosystem/java_jdbc/home.html#setting-up-a-data-source
;
; FIXME (cmhobbs) cause this function to consume and return a canonical
;       JDBC connection string in the form of:
;       jdbc:postgresql://username:password@hostname/dbname 
(def db-spec
  (env :database-url))

(defqueries "sql/queries.sql" {:connection db-spec})
