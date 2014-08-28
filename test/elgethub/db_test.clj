(ns elgethub.db-test
  (:require [elgethub.db :refer :all]
            [clojure.test :refer :all]))

(defn remove-id [rm]
  (dissoc rm :_id))

(deftest maps
  (testing "insert and retrieve a map"
    (let [m    {:a (rand-int 1000000)
                :b (rand-int 1000000)
                :c (rand-int 1000000)}
          _    (put-recipe m)
          out1 (get-recipes m)
          out2 (by-id (-> out1 first :_id))]
      (is (= m (remove-id (first out1))))
      (is (= m (remove-id out2)))
      (is (= (first out1) out2)))))
