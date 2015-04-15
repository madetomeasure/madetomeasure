;; migrations/20150415145452076-updated_at_trigger.clj

(defn up []
  ["CREATE OR REPLACE FUNCTION update_modified_timestamp() RETURNS TRIGGER 
    LANGUAGE plpgsql
    AS
    $$
    BEGIN
        IF (NEW != OLD) THEN
            NEW.updated_at = CURRENT_TIMESTAMP;
            RETURN NEW;
        END IF;
        RETURN OLD;
    END;
    $$"])

(defn down []
  ["DROP FUNCTION update_modified_timestamp()"])
