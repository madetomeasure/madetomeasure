;; migrations/20150514202020621-entity-name-on-subscribers.clj

(defn up []
  ["ALTER TABLE subscribers ADD COLUMN entity_name varchar(255)"])

(defn down []
  ["ALTER TABLE subscribers DROP COLUMN entity_name"])
