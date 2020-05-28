(ns todo.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [todo.middleware :as middleware]
            [todo.routes :refer [html-routes todo-api]]))


(def default-routes
  (routes
   #'todo-api
   #'html-routes
   (route/not-found "Page not found")))


(def app
  (middleware/wrap-base
   #'default-routes))
