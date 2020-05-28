(ns todo.db
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc  :as jdbc]
            [mount.core :refer [defstate] :as m]
            [todo.config :refer [env]])

  (:import [com.zaxxer.hikari HikariConfig HikariDataSource]))


(defn make-config
  [{:keys [jdbc-url
           driver
           username
           password
           auto-commit?
           conn-timeout
           idle-timeout
           max-lifetime
           min-idle
           max-pool-size
           pool-name]}]
  (let [cfg (HikariConfig.)]
    (when jdbc-url             (.setJdbcUrl cfg jdbc-url))
    (when driver               (.setDriverClassName cfg driver))
    (when username             (.setUsername cfg username))
    (when password             (.setPassword cfg password))
    (when (some? auto-commit?) (.setAutoCommit cfg auto-commit?))
    (when conn-timeout         (.setConnectionTimeout cfg conn-timeout))
    (when idle-timeout         (.setIdleTimeout cfg conn-timeout))
    (when max-lifetime         (.setMaxLifetime cfg max-lifetime))
    (when max-pool-size        (.setMaximumPoolSize cfg max-pool-size))
    (when min-idle             (.setMinimumIdle cfg min-idle))
    (when pool-name            (.setPoolName cfg pool-name))
    cfg))

(defn connect! [pool-spec]
  {:datasource
   (HikariDataSource.
    (make-config pool-spec))})


(defn disconnect! [db]
  (when-let [ds (:datasource db)]
    (when-not (.isClosed ds)
      (.close ds))))


(defstate ^:dynamic *db*
  :start (connect!   {:jdbc-url (-> env :jdbc :url)
                      :driver   (-> env :jdbc :driver)
                      :username (-> env :jdbc :username)
                      :password (-> env :jdbc :password)
                      :max-pool-size (-> env :jdbc :max-pool-size)})
  :stop (disconnect! *db*))
