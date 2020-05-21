(ns backend.middleware
  (:require [clojure.tools.logging :as log]
            [ring.middleware.json :refer [wrap-json-body wrap-json-params wrap-json-response]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.util.request :refer [request-url]]
            [ring.util.http-response :refer [found]]
            [backend.config :refer [env]]
            [backend.env :refer [defaults]]
            [backend.layout :refer [error-page]]))



(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t)
        (error-page {:status 500
                     :message (if (:dev env)
                                (or (.getMessage t) "Internal Server Error")
                                "Internal Server Error")})))))




(defn wrap-base [handler & [session]]
  (-> ((:middleware defaults) handler)
      wrap-json-response
      (wrap-json-body :keywords? true :bigdecimals? true)
      (wrap-cookies {:path "/"})
      wrap-keyword-params
      wrap-json-params
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)
           (assoc :session session)))
      wrap-internal-error))
