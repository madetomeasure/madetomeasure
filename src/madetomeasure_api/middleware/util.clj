(ns madetomeasure-api.middleware.util)

(defn make-type-request-pred
  "Function that returns a predicate fn checking if *Content-Type*
   request header matches a specified regexp and body is set."
  [regexp]
  (fn [{:keys [body] :as req}]
    (if-let [#^String type (get req :content-type
                                (get-in req [:headers "Content-Type"]
                                        (get-in req [:headers "content-type"])))]
      (and body (not (empty? (re-find regexp type)))))))

(def json-request?
  (make-type-request-pred #"^application/(vnd.+)?json"))


