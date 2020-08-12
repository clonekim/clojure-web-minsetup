(ns devtools.core
  (:gen-class)
  (:require [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [integrant.core :as ig]
            [org.httpkit.server :as http]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response status]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [com.walmartlabs.lacinia :refer [execute]]
            [devtools.graphql :as graphql]))


(def config
  (ig/read-string (slurp (clojure.java.io/resource "config.edn"))))



(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t)
        (-> (response {:errors (or (.getMessage t) "Internal Server Error")})
            (status 500))))))


(defmethod ig/init-key :http/graphql [_ {:keys [schema]}]
  (do
    (log/info "initializing schema")
    (graphql/init-schema schema)))


(defmethod ig/init-key :http/server [_ {:keys [handler port]}]
  (let [server (http/run-server handler {:port port})]
    (log/info "Starting HTTP server on port" port)
    server))


(defmethod ig/halt-key! :http/server [_ server]
  (do
    (server :timeout 100)
    (log/info "HTTP server stopped")))



(defmethod ig/init-key :handler/app [_ {:keys [schema]}]
  (let [app (routes
             (GET "/" [] "Hello World!")
             (POST "/graphql" req
               (let [query (:query
                             (json/read-str (slurp (:body req)) :key-fn keyword))]
                 (log/debug "Query ->" query)
                 {:status 200
                  :headers {"Content-Type" "application/json"}
                  :body (json/write-str (execute schema query nil nil))}))
              (route/not-found "404 Not Found"))]

    (wrap-defaults
     (-> app
         wrap-internal-error)
     (->  site-defaults
          (assoc-in [:security :anti-forgery] false)))))

(defonce component (atom nil))

(defn start! []
  (reset! component (ig/init config)))

(defn stop! []
  (when-not (nil? @component)
    (ig/halt! @component)
    (reset! component nil)))


(defn restart! []
  (do
    (stop!)
    (start!)))

(defn -main []
  (start!))
