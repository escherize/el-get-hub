(ns elgethub.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(def ^:private db (mg/get-db (mg/connect) "elgethub"))

(defn put [r]
  (mc/insert-and-return db "recipes" (merge r {:_id (ObjectId.)})))

(defn get [query-map]
  (mc/find-maps db "recipes" query-map))


