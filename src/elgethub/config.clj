(ns elgethub.config)

(def local-db
  {:dbtype "postgresql"
   :dbname "elgethub"
   :user   "elgethub_user"
   :host   "127.0.0.1"
   :port   5432})


(def db local-db)
