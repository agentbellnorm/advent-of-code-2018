(ns advent-of-code-2018.common
  (:require [clojure.test :refer :all]
            [clojure.string :as string]))

(defn clean-input
  {:test #(do
            (is (= (clean-input "5\n-5") [5 -5])))}
  [input-str]
  (map read-string (string/split input-str #"\n")))
