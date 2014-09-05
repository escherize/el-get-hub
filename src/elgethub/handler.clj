(ns elgethub.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [elgethub.recipe :as recipe]
            [elgethub.db :as db]))

(defn signup [r]
  (let [email (-> r :query-params (get "email"))]
    (when email
      (db/put-into-store "email" (dissoc r :body))
      (spit "users.txt" (str email "\n") :append true))))

(defn static-page [path]
  (resp/file-response path
                      {:root "resources/public"}))

(defn home []
  (static-page "index.html"))

(defn upload []
  (static-page "upload.html"))

(defn post-signup [email]
  (println "post-signedup" email)
  (db/put-into-store "email" {:email email})
  (spit "users.txt" (str email "\n") :append true)
  (home))

(defroutes app-routes
  (GET "/" [] (home))
  (POST "/" [email] (post-signup email))
  (GET "/recipe/:id" [id] (recipe/page id))
  (GET "/upload" []  (resp/file-response "/upload.html"
                                         {:root "resources/public"}))
  (POST "/upload" [title recipe description]
        (recipe/upload {:title (or title (str "title-" recipe))
                        :recipe recipe}))
  (POST "/search" [title name type] (recipe/find {:title title
                                                  :name name
                                                  :type type}))
  (GET "/site2" [] (static-page "/home.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
