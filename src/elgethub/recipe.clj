(ns elgethub.recipe
  (:require [clojure.string :as s]
            [elgethub.config :as config]
            [elgethub.util :as u]
            [elgethub.db :as db]
            [elgethub.templates.recipe-page :as rt]
            [elgethub.schema :as es]))

(defn format-recipe [recipe]
  (let [chopped-strs (-> recipe
                         (s/split #":")
                         (->> (map s/trim)))]
    (str (first chopped-strs) ":"
         (apply str (interpose
                     "\n       :"
                     (rest chopped-strs))))))

(defn page [id]
  (when-let [{:keys [name recipe description]} (db/by-id id)]
    (->> (rt/recipe-page {:title name
                          :str   (format-recipe recipe)
                          :desc  description})
         (apply str))))

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

(defn remove-empty-kvs [m]
  (into {}
        (for [[k v] m
              :when (not (= "" v))]
          [k v])))

(defn find [big-query-map]
  (let [query-map (remove-empty-kvs big-query-map)]
    (if (= {} query-map)
      "Please enter something :("
      (str "<pre>"
           (apply str (map (comp format-recipe :recipe) (db/get-recipes query-map)))
           "</pre>"))))

(comment

  (defn validate-recipe [r-str]
    (re-seq #"[ ]*:.*|\(:.*" r-str))

  (defn recipe->map [recipe]
    (let [chopped-strs (-> recipe
                           (s/split #":")
                           (->> (map s/trim)))]
      (rest chopped-strs)
      ))

  (def r (slurp "../el-get/recipes/org-mode.rcp"))

  (defn process-recipe [recipe-str]
    (conj (->>
           (s/split recipe-str #"\(:|[ ]+:")
           (map s/trim)
           rest
           (map (fn [kv]
                  [(-> (s/split kv #" ") first keyword)
                   (->> (s/split kv #" ") rest (s/join " "))]))
           (into {}))
          [:recipe recipe-str]))


  )
