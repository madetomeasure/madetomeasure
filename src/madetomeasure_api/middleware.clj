(ns madetomeasure-api.middleware
  (:require [madetomeasure-api.session :as session]
            [taoensso.timbre :as timbre]
            [environ.core :refer [env]]
            [selmer.middleware :refer [wrap-error-page]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.session-timeout :refer [wrap-idle-session-timeout]]
            [noir-exception.core :refer [wrap-internal-error]]
            [ring.middleware.session.memory :refer [memory-store]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth.backends.session :refer [session-backend]]
            ))

(defn log-request [handler]
  (fn [req]
    (timbre/debug req)
    (handler req)))

(defn development-middleware [handler]
  (if (env :dev)
    (-> handler
        wrap-error-page
        wrap-exceptions)
    handler))

(defn production-middleware [handler]
  (-> handler
      (wrap-authentication (session-backend))
      (wrap-restful-format :formats [:json-kw :edn :transit-json :transit-msgpack])
      (wrap-idle-session-timeout
        {:timeout (* 60 30)
         :timeout-response (redirect "/")})
      (wrap-defaults
        (-> site-defaults
          (assoc-in [:security :anti-forgery] false) ;;disable anti-forgery
          (assoc-in [:session :store] (memory-store session/mem))))
      (wrap-internal-error :log #(timbre/error %))))
