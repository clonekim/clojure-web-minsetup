(ns todo.middleware
  (:require [clojure.tools.logging :as log]
            [ring.middleware.json :refer [wrap-json-body wrap-json-params wrap-json-response]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.util.request :refer [request-url]]
            [ring.util.http-response :refer [found]]
            [slingshot.slingshot :refer [try+ throw+]]
            [todo.config :refer [env]]
            [todo.env :refer [defaults]]
            [todo.layout :refer [error-page]]))



(defn wrap-internal-error [handler]
  (fn [req]
    (try+
     (handler req)
     (catch [:type :validation] {:keys [message code]}
       (log/error message)
       (error-page  400 message))
     (catch Throwable t
       (log/error t)
       (error-page 500 (if (:dev env)
                         (or (.getMessage t) "Internal Server Error")
                         "Internal Server Error"))))))



(defn wrap-base [handler & [session]]
  (-> ((:middleware defaults) handler)
      wrap-json-response
      (wrap-json-body :keywords? true :bigdecimals? true)
      (wrap-cookies {:path "/"})
      wrap-keyword-params
      wrap-json-params
      (wrap-resource "build")
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)
           (assoc :session session)))
      wrap-internal-error))
