(ns elgethub.recipe
  (:require [clojure.string :as s]
            [elgethub.config :as config]
            [elgethub.util :as u]
            [elgethub.db :as db]
            [elgethub.schema :as es]))

(defn format-recipe [recipe]
  (let [chopped-strs (-> recipe
                         (s/split #":")
                         (->> (map s/trim)))]
    (str (first chopped-strs) ":"
         (apply str (interpose
                     "\n       :"
                     (rest chopped-strs))))))

(defn process-recipe [recipe-str]
  (conj (->>
         (s/split recipe-str #":|[ ]+:")
         (map s/trim)
         rest
         (map (fn [kv]
                [(-> (s/split kv #" ") first keyword)
                 (->> (s/split kv #" ") rest (s/join " "))]))
         (map (fn [[k v]]
                [k (if (= \) (last v))
                     (apply str (drop-last v))
                     v)]))
         (into {}))
        [:recipe recipe-str]))

(defn upload [{:keys [title recipe description] :as recipe-request}]
  (pr-str
   (cond

    (not (es/is-recipe recipe-request))
    (str "Sorry, your recipe is invalid because: " (es/is-recipe recipe-request))

    :else
    (db/put-recipe (process-recipe recipe)))))

(defn remove-empty [m]
  (into {}
        (for [[k v] m
              :when (not (= "" v))]
          [k v])))

(defn find [big-query-map]
  (let [query-map (remove-empty big-query-map)]
    (if (= {} query-map)
      ["error, empty query map"]
      (->> (db/get-recipes query-map)
           (map :recipe)
           (map format-recipe)))))

