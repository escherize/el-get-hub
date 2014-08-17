(defproject elgethub "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure       "1.6.0"]
                 [compojure                 "1.1.8"]
                 [prismatic/schema          "0.2.6"]
                 [org.clojure/java.jdbc     "0.3.5"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [enlive                    "1.1.5"]
                 [jry                       "2.0.0"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler elgethub.handler/app
         :nrepl {:start? true, :port 5454}}
  :profiles {:dev
             {:dependencies [[javax.servlet/servlet-api "2.5"]
                             [ring-mock "0.1.5"]]}})
