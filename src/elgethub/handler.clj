(ns elgethub.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [elgethub.recipe :as recipe]))

(defroutes app-routes
  (GET "/" [] (resp/file-response "index.html" 
                                  {:root "resources/public"}))
  (GET "/recipe/:id" [id] (or (recipe/page id)
                              "Not Found"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
