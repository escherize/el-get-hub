(ns elgethub.recipe
  (:require [clojure.java.jdbc :as j]
            [clojure.string :as s]
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

(defn insert! [{:keys [id name recipe-str description :as row-map]}]
  (j/insert! config/db "recipes"
             ["id" "name" "recipe_str" "description"]
             [id name recipe-str description]))

(defn pretty-print [recipe-str]
  (let [kv-strs (-> recipe-str
                    (s/split #":")
                    (->> (map s/trim)))]
    (str (first kv-strs) ":" (apply str (interpose "\n :" (rest kv-strs))))))

(defn page [id]
  (when-let [{:keys [id name recipe-str description :as recipe]} (by-id id)]
    (->> (rt/recipe {:recipe-title name
                     :recipe-str (pretty-print recipe-str)
                     :recipe-desc description})
         (apply str))))

(comment
;; Sun, Aug 17, 2014 bryan -> add tests for pp:
  (map pretty-print
       ["(:name ac-octave
          :type emacswiki
          :description \"octave completions support for auto-complete\")"
        "
 (:name ac-octave
        :type emacswiki
        :description \"octave completions support for auto-complete\")"
        "(:name magit :other-thing \"is another string like thing\""])



  (insert! {:id "530"
            :name "octave"
            :description "This is a bit longer of a description.  Even longer looks like this. Also I use it tons for the fun shit you can do with it. So fun very fun."
            :recipe-str "
 (:name ac-octave
        :type emacswiki
        :description \"octave completions support for auto-complete\")"})

  )
