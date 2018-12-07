(ns advent-of-code-2018.day-06
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.common :refer :all]
            [clojure.set :refer :all]
            [clojure.string :refer [split-lines includes? upper-case lower-case]]))

(defn abs [n] (max n (- n)))

(defn n-chars [n]
  (map #(str %) (range n)))

(defn parse
  {:test (fn [] (is (= (parse "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9") {"0" [1 1] "1" [1 6] "2" [8 3] "3" [3 4] "4" [5 5] "5" [8 9]})))}
  [input]
  (as-> input $
        (split-lines $)
        (map (fn [line]
               (let [[x y] (clojure.string/split line #",\s")]
                 [(read-string x) (read-string y)])) $)
        (zipmap (n-chars (count $)) $)))

(def test-input (parse "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9"))

(def input (parse "154, 159\n172, 84\n235, 204\n181, 122\n161, 337\n305, 104\n128, 298\n176, 328\n146, 71\n210, 87\n341, 195\n50, 96\n225, 151\n86, 171\n239, 68\n79, 50\n191, 284\n200, 122\n282, 240\n224, 282\n327, 74\n158, 289\n331, 244\n154, 327\n317, 110\n272, 179\n173, 175\n187, 104\n44, 194\n202, 332\n249, 197\n244, 225\n52, 127\n299, 198\n123, 198\n349, 75\n233, 72\n284, 130\n119, 150\n172, 355\n147, 314\n58, 335\n341, 348\n236, 115\n185, 270\n173, 145\n46, 288\n214, 127\n158, 293\n237, 311"))

(defn closest
  {:test (fn []
           (is (= (first (closest test-input [2 2])) "0"))
           (is (= (first (closest test-input [0 4])) "-1"))
           )}
  [places [p1 p2]]
  (let [distances (reduce (fn [acc [place [q1 q2]]]
                            (let [distance (+ (abs (- p1 q1)) (abs (- p2 q2)))]
                              (assoc acc place distance))) {} places)
        closest (apply min-key val distances)
        frequency-map (frequencies (vals distances))]
    (if (< 1 (get frequency-map (second closest)))
      ["-1"]
      closest)))

(defn distance-to-others
  {:test (fn []
           (is (= (distance-to-others test-input [4 3]) 30)))}
  [places [p1 p2]]
  (let [distances (reduce (fn [acc [place [q1 q2]]]
                            (let [distance (+ (abs (- p1 q1)) (abs (- p2 q2)))]
                              (assoc acc place distance))) {} places)]
    (apply + (vals distances))))

(defn bounding-box
  {:test (fn []
           (is (= (bounding-box test-input) [1 1 9 10])))}
  [places]
  (let [min-x (apply min (map #(first %) (vals places)))
        min-y (apply min (map #(second %) (vals places)))
        max-x (apply max (map #(first %) (vals places)))
        max-y (apply max (map #(second %) (vals places)))]
    [min-x min-y (inc max-x) (inc max-y)]))

(defn init-space
  {:test (fn []
           (is (= (init-space test-input closest) [[["0" 0] ["0" 1] ["0" 2] ["0" 3] ["-1"] ["2" 4] ["2" 3] ["2" 2]]
                                                   [["0" 1] ["0" 2] ["3" 2] ["3" 3] ["4" 3] ["2" 3] ["2" 2] ["2" 1]]
                                                   [["0" 2] ["3" 2] ["3" 1] ["3" 2] ["4" 2] ["2" 2] ["2" 1] ["2" 0]]
                                                   [["-1"] ["3" 1] ["3" 0] ["3" 1] ["4" 1] ["4" 2] ["2" 2] ["2" 1]]
                                                   [["1" 1] ["-1"] ["3" 1] ["4" 1] ["4" 0] ["4" 1] ["4" 2] ["2" 2]]
                                                   [["1" 0] ["1" 1] ["-1"] ["4" 2] ["4" 1] ["4" 2] ["4" 3] ["-1"]]
                                                   [["1" 1] ["1" 2] ["-1"] ["4" 3] ["4" 2] ["4" 3] ["5" 3] ["5" 2]]
                                                   [["1" 2] ["1" 3] ["-1"] ["4" 4] ["4" 3] ["5" 3] ["5" 2] ["5" 1]]
                                                   [["1" 3] ["1" 4] ["-1"] ["5" 4] ["5" 3] ["5" 2] ["5" 1] ["5" 0]]])))}
  [coords strategy]
  (let [[min-x min-y max-x max-y] (bounding-box coords)
        empty-space (vec (repeat max-y (vec (repeat max-x 0))))]
    (->> empty-space
         (map-indexed (fn [row-idx row-val]
                        (->> row-val
                             (map-indexed (fn [col-idx _]
                                            (strategy coords [col-idx row-idx])))
                             (drop min-x)
                             (vec))))
         (drop min-y)
         (vec))))

(defn top
  {:test (fn [] (is (= (top (init-space test-input closest)) #{"0" "2"})))}
  [coords]
  (difference (set (map first (first coords))) #{"-1"}))

(defn bottom
  {:test (fn [] (is (= (bottom (init-space test-input closest)) #{"1" "5"})))}
  [coords]
  (difference (set (map first (last coords))) #{"-1"}))

(defn left
  {:test (fn [] (is (= (left (init-space test-input closest)) #{"0" "1"})))}
  [coords]
  (difference (set (map (fn [row]
                          (first (first row))) coords)) #{"-1"}))
(defn right
  {:test (fn [] (is (= (right (init-space test-input closest)) #{"2" "5"})))}
  [coords]
  (difference (set (map (fn [row]
                          (first (last row))) coords)) #{"-1"}))

(defn area
  {:test (fn []
           (is (= (area (init-space test-input closest) "4") 17)))}
  [inited-space id]
  (->> inited-space
       (reduce (fn [current-area row]
                 (+ current-area
                    (or (get (frequencies (map first row)) id)
                        0))) 0)))

(defn biggest-non-infinite-area
  {:test (fn []
           (is (= (biggest-non-infinite-area test-input) 17))
           (comment (is (= (biggest-non-infinite-area input) 4186))))} ; part 1
  [coords]
  (let [space (init-space coords closest)
        all-point-ids (keys coords)
        bordering-ids (union (top coords) (left coords) (bottom coords) (right coords))
        inner-ids (difference all-point-ids bordering-ids)]
    (->> inner-ids
         (pmap #(area space %))
         (apply max))))

(defn safe-region-size
  {:test (fn []
           (is (= (safe-region-size test-input 32) 16))
           (is (= (safe-region-size input 10000) 45509)))}  ; part 2
  [coords threshold]
  (reduce (fn [region-size row]
            (+ region-size
               (count (filter #(> threshold %) row))))
          0
          (init-space coords distance-to-others)))
