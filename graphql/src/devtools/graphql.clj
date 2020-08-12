(ns devtools.graphql
  (:require [clojure.edn :as edn]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as s]))



(defn init-schema [schema-name]
  (-> (clojure.java.io/resource schema-name)
      slurp
      edn/read-string
      (attach-resolvers
       {:query/get-user (constantly {})
        :mutation/add-user (constantly {})
        :mutation/add-db (constantly {})
        })
      s/compile)
)
