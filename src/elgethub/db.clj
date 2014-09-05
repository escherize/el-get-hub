(ns elgethub.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(def ^:private db (mg/get-db (mg/connect) "elgethub"))

(defn ObjectId->str [^ObjectId x]
  (.toString x))

(defn put-into-store [store map]
  (update-in
   (mc/insert-and-return db store (merge map {:_id (ObjectId.)}))
   [:_id] ObjectId->str))

(defn get-from-store [store {:keys [_id] :as query-map}]
  (->> (mc/find-maps db "recipes" (if _id (merge query-map
                                                 {:_id (ObjectId. _id)})
                                      query-map))
       (map #(update-in % [:_id] ObjectId->str))))

(defn put-recipe [r]
  "Inserts a recipe-map into mongo, returns the map,
   with a string :_id from the recipes store"
  (put-into-store "recipes" r))

(defn get-recipes [query-map]
  "Get a recipe from the recipes store"
  (get-from-store "recipes" query-map))

(defn by-id [id]
  (first (get-recipes {:_id id})))
