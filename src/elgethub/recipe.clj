(ns elgethub.recipe
  (:require [clojure.java.jdbc :as j]
            [elgethub.config :as config]
            [elgethub.templates.recipe-page :as rt]
            [jry :refer [update-keys]]))

(defn kebab-keys [m]
  (update-keys m #(-> % name (clojure.string/replace "_" "-") keyword)))

(defn unkebab-keys [m]
  (update-keys m #(-> % name (clojure.string/replace "-" "_") keyword)))

(defn by-id [id]
  (-> (j/query config/db [ "select * from recipes where id = ?" (str id)])
      first
      kebab-keys))

(defn insert! [{:keys [id name recipe-str :as row-map]}]
  (j/insert! config/db "recipes"
             ["id" "name" "recipe_str" "description"]
             [id name recipe-str description]))

(defn page [id]
  (when-let [{:keys [id name recipe-str :as recipe]} (by-id id)]
    (->> (rt/recipe {:recipe-title name
                     :recipe-str recipe-str})
         (apply str))))

(comment
  "usage: "
  (insert! {:id "3"
            :name "markdown-mode"
            :description ""
            :recipe-str "(:name markdown-mode :type github)"})


  )
