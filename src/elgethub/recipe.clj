(ns elgethub.recipe
  (:require [clojure.string :as s]
            [elgethub.config :as config]
            [elgethub.util :as u]
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
  (when-let [{:keys [id name recipe-str description :as recipe]} {:id "id"
                                                                  :name "name"
                                                                  :recipe-str "recipe-str"
                                                                  :description "description"}]
    (->> (rt/recipe-page {:title name
                          :str   (format-recipe recipe-str)
                          :desc  description})
         (apply str))))

(comment
  (insert! {:id "530"
            :name "octave"
            :description "This is a bit longer of a description.  Even longer looks like this. Also I use it tons for the fun shit you can do with it. So fun very fun."
            :recipe-str "
 (:name ac-octave
        :type emacswiki
        :description \"octave completions support for auto-complete\")"})

  )
