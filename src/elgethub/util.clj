(ns elgethub.util
  (:require [clojure.string :as s]
            [jry :refer [update-keys]]))

(defn kebab-keys [m]
  (update-keys m #(-> % name (s/replace "_" "-") keyword)))

(defn unkebab-keys [m]
  (update-keys m #(-> % name (s/replace "-" "_") keyword)))

