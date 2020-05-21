(ns backend.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [backend.middleware :as middleware]
            [backend.routes :refer [html-routes]]))


(def default-routes
  (routes
   #'html-routes
   (route/not-found "Page not found")))


(def app
  (middleware/wrap-base
   #'default-routes))
