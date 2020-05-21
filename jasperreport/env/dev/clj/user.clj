(ns user
  (:require [mount.core :as mount]
            backend.core))

(defn start []
  (mount/start-without #'backend.core/repl-server))

(defn stop []
  (mount/stop-except #'backend.core/repl-server))

(defn restart []
  (stop)
  (start))
