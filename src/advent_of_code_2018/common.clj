(ns advent-of-code-2018.common
  (:require [clojure.test :refer :all]
            [clojure.string :as string]))

(defn read-input-as-symbols
  {:test #(do
            (is (= (read-input-as-symbols "5\n-5") [5 -5])))}
  [input-str]
  (map read-string (string/split input-str #"\n")))

(defn read-input-as-strings
  {:test #(do
            (is (= (read-input-as-strings "abcdef\nbabab") ["abcdef" "babab"])))}
  [input-str]
  (string/split input-str #"\n"))

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

;; From e.g. https://github.com/scottjad/uteal
(defmacro dlet
  "let with inspected bindings"
  [bindings & body]
  `(let [~@(mapcat (fn [[n v]]
                     (if (or (vector? n) (map? n))
                       [n v]
                       [n v '_ `(println (name '~n) ":" ~v)]))
                   (partition 2 bindings))]
     ~@body))

(defn regex-to-int
  {:test (fn []
           (is (regex-to-int "[1518-11-01 00:05] falls asleep"  #"\d{2}:(.*)\]") 5))}
  [str pat]
  (Integer/parseInt (last (re-find pat str))))

(defn regex-to-str
  {:test (fn []
           (is (regex-to-str "[1518-11-01 00:05] falls asleep"  #"\d{2}:(.*)\]") "05"))}
  [str pat]
  (last (re-find pat str)))
