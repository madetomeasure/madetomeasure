(ns madetomeasure-api.test.routes.subscribers
  (:require [madetomeasure-api.db.core :as db])
  (:use clojure.test
        cheshire.core
        ring.mock.request
        madetomeasure-api.handler))

(defn json-post [endpoint body]
  (-> 
    (request :post endpoint)
    (body (.getBytes body "UTF-8"))
    (header {"Content-Type" "application/json"})))

(deftest subscribers
         (testing "creation"
                  (let [mock-subscriber (generate-string {:address "flap@flap.com"})
                        bad-request (generate-string {:flap "blap"})]
                    (testing "valid subscriber post"
                             (let [response (app (json-post "/subscribers" mock-subscriber))]
                               (is (= 200 (:status response)))))
                    (testing "error subscriber post"
                            (let [response (app (json-post "/subscribers" ""))]
                              (is (= 400 (:status response)))))
                    (testing "fail subscriber post"
                             (let [response (app (json-post "/subscribers" bad-request))]
                               (is (= 400 (:status response))))))))
