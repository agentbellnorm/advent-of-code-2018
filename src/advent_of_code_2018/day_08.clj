(ns advent-of-code-2018.day-08
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.common :refer :all]
            [clojure.set :refer :all]
            [clojure.string :refer [split trim split-lines includes? upper-case lower-case]]
            [clojure.string :as string]))

(def test-input (split "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2" #"\s"))

(defn init-tree
  {:test (fn []
           (is (= (init-tree test-input)))
           )}
  [input index]
  (let [[number-of-children number-of-metadata] (take 2 input)
        children (reduce (fn [{next-child-index :next-child-index
                               children :children} _]
                           (let [current-child-node (init-tree input next-child-index)]
                             {:children         (assoc children next-child-index current-child-node)
                              :next-child-index (+ next-child-index (:length current-child-node))
                              }))
                         {:next-child-index (+ 2 index)}
                         (range number-of-children))]
    {:metadata (subvec (:next-child-index children) (+ (:next-child-index children)
                                                       number-of-metadata))
     :length   (+ number-of-metadata (- (:next-child-index children) index))
     :children {}}))
