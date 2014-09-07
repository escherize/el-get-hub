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
  (static-page "home.html"))

(defn upload []
  (static-page "upload.html"))

(defn post-signup [email]
  (println "post-signedup" email)
  (db/put-into-store "email" {:email email})
  (spit "users.txt" (str email "\n") :append true)
  (home))


(defn search-result-page [srs]
  (str "<pre>"
       (clojure.string/join "<br><br>" srs)
       "</pre>"))

(defroutes app-routes
  (GET "/" [] (home))
  (POST "/" [email] (post-signup email))
  (GET "/upload" []  (resp/file-response "/upload.html"
                                         {:root "resources/public"}))
  (POST "/upload" [title recipe description]
        (recipe/upload {:title (or title (str "title-" recipe))
                        :recipe recipe}))
  (GET "/search" [] #(let [search-results (recipe/find (-> (for [[k v] (:query-params %)]
                                                             [(keyword k) v])
                                                           (into {})))]
                       (search-result-page search-results)))
  (GET "/site2" [] (static-page "/home.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
