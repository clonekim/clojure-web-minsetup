(ns backend.layout
  (:require [selmer.parser :as parser]
            [selmer.filters :as filters]
            [cheshire.core :refer [generate-string]]
            [ring.util.response :refer [content-type response]]
            [backend.config :refer [env]]))

(parser/set-resource-path! (clojure.java.io/resource "templates"))


(defn render
  [template & [params]]
  (content-type
   (response
    (parser/render-file
     template
     (assoc params
            :dev (:dev env true)
            :app-version (:app-version env))))
   "text/html; charset=utf-8"))


(defn error-page [{:keys [message status]}]
  {:status  status
   :headers {"Content-Type" "application/json"}
   :body    (generate-string {:code status :errors message})})
