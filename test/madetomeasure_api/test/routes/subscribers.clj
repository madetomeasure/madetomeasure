(ns madetomeasure-api.test.routes.subscribers
  (:require [madetomeasure-api.db.core :as db])
  (:use clojure.test
        cheshire.core
        ring.mock.request
        madetomeasure-api.handler))

(defn json-post [endpoint body-string]
  (-> 
    (request :post endpoint)
    (body body-string)
    (content-type "application/json")))

(deftest subscribers
         (testing "creation"
                  (let [subscribers '({:address "flap@flap.com"} {:address "flap@gums.com"})
                        mock-subscriber (generate-string (first subscribers))
                        bulk-subscribers (generate-string subscribers)
                        bad-request (generate-string {:flap "blap"})]
                    (testing "valid subscriber post"
                             (let [response (app (json-post "/subscribers" mock-subscriber))]
                               (is (= 200 (:status response)))))
                    (testing "bulk subscriber post"
                             (let [response (app (json-post "/subscribers" bulk-subscribers))]
                               (is (= 200 (:status response)))))
                    (testing "error subscriber post"
                            (let [response (app (json-post "/subscribers" ""))]
                              (is (= 400 (:status response)))))
                    (testing "fail subscriber post"
                             (let [response (app (json-post "/subscribers" bad-request))]
                               (is (= 400 (:status response))))))))
