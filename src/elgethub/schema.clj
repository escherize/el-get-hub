(ns elgethub.schema
  (:require [schema.core :as s]))

(declare Min-Recipe Full-Recipe-Spec)

(def Recipe 
  (s/either Min-Recipe Full-Recipe-Spec))

(def Min-Recipe
  {:title s/Any
   :recipe s/Str
   s/Keyword s/Any})

(defn is-recipe [recipe]
  (s/validate Min-Recipe recipe))

(def User
  {:name s/Str
   :email s/Str
   :pass s/Str})

(def Comment
  {:author User
   :comment s/Str})

(def RecipePage
  "A map with the data for a recipe page"
  {:recipe Recipe
   :comments [Comment]})

;; docs at:
;; https://github.com/dimitri/el-get/blob/master/el-get.info for info
(comment
  (def Full-Recipe-Spec
    {:name s/Str
     (s/optional-key :after) s/Str
     (s/optional-key :autoloads) s/Str
     (s/optional-key :before) s/Str
     (s/optional-key :branch) s/Str
     (s/optional-key :build) s/Str
     (s/optional-key :build/system-type) s/Str
     (s/optional-key :builtin) s/Str
     (s/optional-key :checkout) s/Str
     (s/optional-key :checksum) s/Str
     (s/optional-key :compile) s/Str
     (s/optional-key :depends) s/Str
     (s/optional-key :description) s/Str
     (s/optional-key :features) s/Str
     (s/optional-key :info) s/Str
     (s/optional-key :lazy) s/Str
     (s/optional-key :library) s/Str
     (s/optional-key :load) s/Str
     (s/optional-key :load-path) s/Str
     (s/optional-key :localname) s/Str
     (s/optional-key :module) s/Str
     (s/optional-key :options) s/Str
     (s/optional-key :pkgname) s/Str
     (s/optional-key :post-init) s/Str
     (s/optional-key :prepare) s/Str
     (s/optional-key :repo) s/Str
     (s/optional-key :shallow) s/Str
     (s/optional-key :submodule) s/Str
     (s/optional-key :type) s/Str
     (s/optional-key :url) s/Str
     (s/optional-key :username) s/Str
     (s/optional-key :website) s/Str}))


