(defproject devtools "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [org.clojure/data.json "1.0.0"]
                 [integrant "0.8.0"]
                 [http-kit "2.4.0"]
                 [compojure "1.6.2"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [com.walmartlabs/lacinia "0.37.0"]]
  :repl-options {:init-ns devtools.core}
  :main devtools.core
  :profiles
  {:uberjar
   {:omit-source true
    :uberjar-name "graphql-impl.jar"
    :uberjar-exclusions [#"META-INF/(leiningen|maven)"]
    :aot :all}
   })
