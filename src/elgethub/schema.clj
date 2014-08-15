(ns elgethub.schema
  (:require [schema.core :as s]))

;; docs at:
;; https://github.com/dimitri/el-get/blob/master/el-get.info for info

(def User
  {:name s/Str
   :email s/Str})

(def Recipe
  :name s/Str
  (s/optional-key :depends) [s/Str]
  (s/optional-key :builtin)
  {s/optional-key})

(def Comment
  {:author User
   :comment s/Str})

(def RecipePage
  "A map with the data for a recipe page"
  {:recipe Recipe
   :comments [Comment]})
