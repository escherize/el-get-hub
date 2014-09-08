(ns elgethub.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [elgethub.recipe :as recipe]
            [elgethub.html :as static]
            [elgethub.db :as db]))

(defn signup [r]
  (let [email (-> r :query-params (get "email"))]
    (when email
      (db/put-into-store "email" (dissoc r :body))
      (spit "users.txt" (str email "\n") :append true))))

(defn static-page [path]
  (resp/file-response path
                      {:root "resources/public"}))

(defn signup [email]
  (db/put-into-store "email" {:email email})
  (home))

(defn search-result-page [srs]
  (if-not (seq srs)
    "Sorry, no results."
    (str "<pre>"
         (clojure.string/join "<br><br>" srs)
         "</pre>")))

(defroutes app-routes
  (GET "/"            [] (static/home-page))
  (GET "/home.html"   [] (static/home-page))
  (GET "/upload.html" [] (static/upload-page))
  (GET "/search.html" [] (static/search-page))

  (GET "/find" [] #(let [search-results (recipe/find (-> (for [[k v] (:query-params %)]
                                                           [(keyword k) v])
                                                         (into {})))]
                     (search-result-page search-results)))
  (POST "/" [email] (signup email))

  (POST "/upload" [title recipe description]
        (recipe/upload {:title (or title (str "title-" recipe))
                        :recipe recipe}))

  (GET "/site2" [] (static-page "/home.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
