(ns advent-of-code-2018.common
  (:require [clojure.test :refer :all]
            [clojure.string :as string]))

(defn clean-input
  {:test #(do
            (is (= (clean-input "5\n-5") [5 -5])))}
  [input-str]
  (map read-string (string/split input-str #"\n")))

(defn circular-nth
  {:test (fn []
           (is (= (circular-nth [1 2] 3) 1))
           (is (= (circular-nth [1] 1) 1))
           (is (= (circular-nth [1] 2) 1))
           (is (= (circular-nth [1 2 3 4 5] 7) 2)))}
  [col n]
  (nth col (mod (dec n) (count col))))

(defn printreturn
  [x]
  (println x)
  x)
