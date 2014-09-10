(ns elgethub.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [elgethub.recipe :as recipe]
            [elgethub.html :as pages]
            [elgethub.db :as db]
            [jry]))

(defn signup [r]
  (let [email (-> r :query-params (get "email"))]
    (when email
      (db/put-into-store "email" (dissoc r :body))
      (spit "users.txt" (str email "\n") :append true))))

(defn signup [email]
  (db/put-into-store "email" {:email email})
  (pages/home-page))

(defroutes app-routes
  (GET "/"            [] (pages/home-page))
  (GET "/home.html"   [] (pages/home-page))
  (GET "/upload.html" [] (pages/upload-page))
  (GET "/search.html" [] (pages/search-page))
  (GET "/find" [] #(let [search-results (recipe/find
                                         (-> (for [[k v] (:query-params %)]
                                               [(keyword k) v])
                                             (into {})))]
                     (pages/search-result-page search-results)))

  (POST "/" [email] (signup email))
  (POST "/upload" [title recipe description]
        (recipe/upload {:title (or title (str "title-" recipe))
                        :recipe recipe}))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
