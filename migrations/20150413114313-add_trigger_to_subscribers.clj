;; migrations/20150415145706141-add_trigger_to_subscribers.clj

(defn up []
  ["CREATE TRIGGER t_subscribers_updated_at BEFORE UPDATE ON subscribers FOR EACH ROW EXECUTE PROCEDURE update_modified_timestamp()"])

(defn down []
  ["DROP TRIGGER IF EXISTS t_subscribers_updated_at ON subscribers"])
