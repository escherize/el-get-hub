(ns elgethub.recipe
  (:require [clojure.string :as s]
            [elgethub.config :as config]
            [elgethub.util :as u]
            [elgethub.db :as db]
            [elgethub.templates.recipe-page :as rt]))

(defn format-recipe [recipe-str]
  (let [chopped-strs (-> recipe-str
                         (s/split #":")
                         (->> (map s/trim)))]
    (str (first chopped-strs) ":"
         (apply str (interpose
                     "\n       :"
                     (rest chopped-strs))))))

(defn page [id]
  (when-let [{:keys [id name recipe-str description :as recipe]} (db/by-id id)]
    (->> (rt/recipe-page {:title name
                          :str   (format-recipe recipe-str)
                          :desc  description})
         (apply str))))

(defn insert-page [request]
  
  (def *r request)
  "hi")

