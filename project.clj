(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))


(defproject backend "0.0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.clojure/tools.logging "1.1.0"]
                 [http-kit "2.3.0"]
                 [cheshire "5.10.0"]
                 [compojure "1.6.1"]
                 [mount "0.1.16"]
                 [nrepl "0.7.0"]
                 [cprop/cprop "0.1.17"]
                 [selmer/selmer "1.12.23" :exclusions [hiccups]]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0" :exclusions [cheshire]]
                 [metosin/ring-http-response "0.9.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]

                 [com.lowagie/iTextAsian "1.0.0"]
                 [net.sf.jasperreports/jasperreports "6.8.0" :exclusions [com.fasterxml.jackson.core/jackson-annotations
                                                                          com.fasterxml.jackson.core/jackson-core
                                                                          com.fasterxml.jackson.core/jackson-databind
                                                                          org.bouncycastle/bcprov-jdk15on
                                                                          org.codehaus.castor/castor-core
                                                                          org.codehaus.castor/castor-xml
                                                                          org.jfree/jcommon
                                                                          org.jfree/jfreechart
                                                                          org.eclipse.jdt.core.compiler/ecj]]]

  :min-lein-version "2.0.0"
  :repl-options {:init-ns backend.core}
  :repositories { "mavenl" "http://10.46.44.5:8081/repository/maven-releases"}
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main backend.core

  :profiles
  {:uberjar {:omit-source true
             :uberjar-name "backend.jar"
             :uberjar-exclusions [#"META-INF/(leiningen|maven)"]
             :aot :all
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev    [:project/dev]
   :prod   [:project/dev :project/prod]
   :project/dev {

                 :dependencies [[pjstadig/humane-test-output "0.10.0"]
                                [prone "2020-01-17"]
                                [ring-logger "1.0.0"]
                                [ring-cors "0.1.12"]
                                [ring/ring-devel "1.8.0"]
                                [ring/ring-mock "0.4.0"]]
                 :plugins      [[com.jakemccrary/lein-test-refresh "0.24.1"]
                                 [jonase/eastwood "0.3.5"]]
                 :source-paths ["env/dev/clj"]
                 :resource-paths ["env/dev/resources"]
                 :repl-options {:init-ns user
                                :timeout 120000}
                 :injections [(require 'pjstadig.humane-test-output)
                              (pjstadig.humane-test-output/activate!)]}
   :project/prod  {:resource-paths ["env/prod/resources"]}}

  )
