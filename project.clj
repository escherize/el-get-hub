(defproject elgethub "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure    "1.6.0"]
                 [compojure              "1.1.8"]
                 [prismatic/schema       "0.2.6"]
                 [com.novemberain/monger "2.0.0"]
                 [jry                    "2.0.0"]
                 [hiccup                 "1.0.5"]
                 [hiccup-bridge          "1.0.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler elgethub.handler/app
         :nrepl {:start? true, :port 5454}}
  :profiles {:dev
             {:dependencies [[ring-mock "0.1.5"]]}})
