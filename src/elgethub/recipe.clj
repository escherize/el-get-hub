(ns elgethub.recipe
  (:require [clojure.java.jdbc :as j]
            [elgethub.config :as config]))

(defn by-id [id]
  (j/query config/db
           (format "select * from recipes where id = '%s'"
                   (str id))))

(comment
  (j/query config/db "select * from recipes;")
  )
