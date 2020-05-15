(ns backend.routes
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [compojure.core :refer [defroutes context GET POST PUT DELETE PATCH]]
            [cheshire.core :refer [parse-string generate-string]]
            [ring.util.http-response :refer [created ok not-found bad-request found set-cookie]]
            [backend.config :refer [env]]
            [backend.layout :refer [render]]))



(defroutes html-routes
  (GET "/ping" []
    (ok {:status "pong"}))

  (GET "/" []
    (render "hello.html"  {:name "Bonjour"}))
)
