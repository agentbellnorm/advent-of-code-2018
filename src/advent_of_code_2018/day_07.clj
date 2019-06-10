(ns advent-of-code-2018.day-07
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.common :refer :all]
            [clojure.set :refer :all]
            [clojure.string :refer [trim split-lines includes? upper-case lower-case]]))

(def test-input "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin.")

(def input "Step T must be finished before step X can begin.\nStep G must be finished before step O can begin.\nStep X must be finished before step B can begin.\nStep I must be finished before step W can begin.\nStep N must be finished before step V can begin.\nStep K must be finished before step H can begin.\nStep S must be finished before step R can begin.\nStep P must be finished before step J can begin.\nStep L must be finished before step V can begin.\nStep D must be finished before step E can begin.\nStep J must be finished before step R can begin.\nStep U must be finished before step W can begin.\nStep M must be finished before step Q can begin.\nStep B must be finished before step F can begin.\nStep F must be finished before step E can begin.\nStep V must be finished before step Q can begin.\nStep C must be finished before step A can begin.\nStep H must be finished before step Z can begin.\nStep A must be finished before step Y can begin.\nStep O must be finished before step Y can begin.\nStep W must be finished before step Q can begin.\nStep E must be finished before step Y can begin.\nStep Y must be finished before step Z can begin.\nStep Q must be finished before step R can begin.\nStep R must be finished before step Z can begin.\nStep S must be finished before step E can begin.\nStep O must be finished before step W can begin.\nStep G must be finished before step B can begin.\nStep I must be finished before step N can begin.\nStep G must be finished before step I can begin.\nStep H must be finished before step R can begin.\nStep N must be finished before step C can begin.\nStep M must be finished before step W can begin.\nStep Y must be finished before step R can begin.\nStep T must be finished before step B can begin.\nStep G must be finished before step D can begin.\nStep J must be finished before step O can begin.\nStep I must be finished before step A can begin.\nStep J must be finished before step H can begin.\nStep T must be finished before step Y can begin.\nStep N must be finished before step H can begin.\nStep B must be finished before step V can begin.\nStep M must be finished before step R can begin.\nStep Y must be finished before step Q can begin.\nStep X must be finished before step J can begin.\nStep A must be finished before step E can begin.\nStep P must be finished before step Z can begin.\nStep P must be finished before step C can begin.\nStep N must be finished before step Q can begin.\nStep A must be finished before step O can begin.\nStep G must be finished before step X can begin.\nStep P must be finished before step U can begin.\nStep T must be finished before step S can begin.\nStep I must be finished before step V can begin.\nStep V must be finished before step H can begin.\nStep U must be finished before step F can begin.\nStep D must be finished before step Q can begin.\nStep D must be finished before step O can begin.\nStep G must be finished before step H can begin.\nStep I must be finished before step Z can begin.\nStep N must be finished before step D can begin.\nStep B must be finished before step Y can begin.\nStep J must be finished before step M can begin.\nStep V must be finished before step Y can begin.\nStep W must be finished before step Y can begin.\nStep E must be finished before step Z can begin.\nStep T must be finished before step N can begin.\nStep L must be finished before step U can begin.\nStep S must be finished before step A can begin.\nStep Q must be finished before step Z can begin.\nStep T must be finished before step F can begin.\nStep F must be finished before step Z can begin.\nStep J must be finished before step C can begin.\nStep X must be finished before step Y can begin.\nStep K must be finished before step V can begin.\nStep T must be finished before step I can begin.\nStep I must be finished before step O can begin.\nStep C must be finished before step W can begin.\nStep B must be finished before step Q can begin.\nStep W must be finished before step Z can begin.\nStep D must be finished before step H can begin.\nStep K must be finished before step A can begin.\nStep M must be finished before step E can begin.\nStep T must be finished before step U can begin.\nStep I must be finished before step J can begin.\nStep O must be finished before step Q can begin.\nStep M must be finished before step Z can begin.\nStep U must be finished before step C can begin.\nStep N must be finished before step F can begin.\nStep C must be finished before step H can begin.\nStep X must be finished before step E can begin.\nStep F must be finished before step O can begin.\nStep P must be finished before step O can begin.\nStep J must be finished before step A can begin.\nStep H must be finished before step Y can begin.\nStep A must be finished before step Q can begin.\nStep V must be finished before step Z can begin.\nStep S must be finished before step L can begin.\nStep H must be finished before step E can begin.\nStep X must be finished before step I can begin.\nStep O must be finished before step R can begin.")

(defn all-actions
  {:test (fn []
           (is (= (all-actions test-input) #{"A" "B" "C" "D" "E" "F"}))
           )}
  [raw]
  (->> raw
       (re-seq #" [A-Z] ")
       (map #(trim %))
       (set)))

(defn dependency-map
  {:test (fn []
           (is (= (dependency-map "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep G must be finished before step A can begin.")
                  {"A" #{"C" "G"}
                   "F" #{"C"}
                   "C" #{}
                   "G" #{}})))}
  [input]
  (let [has-dependencies (reduce (fn [dep-map line]
                                   (let [dependency (regex-to-str line #"Step\s([A-Z])\smust")
                                         dependee (regex-to-str line #"step\s([A-Z])\scan")]
                                     (update dep-map dependee (fn [dependency-set]
                                                                (union dependency-set #{dependency}))))) {} (split-lines input))
        not-in-map (reduce (fn [acc key]
                             (assoc acc key #{})) {} (all-actions input))]
    (merge not-in-map has-dependencies)))

(defn next-action
  {:test (fn []
           (is (= (next-action {"A" #{"G"} "B" #{"C" "F"} "F" #{}}) "F"))
           (is (= (next-action {"A" #{} "B" #{"C" "F"} "F" #{}}) "A"))
           (is (= (next-action {"Z" #{} "B" #{"C" "F"} "F" #{}}) "F"))
           (is (= (next-action (dependency-map test-input)) "C"))
           )}
  [dependency-map]
  (->> dependency-map
       (filter #(empty? (val %)))
       (sort-by key)
       ffirst))

(defn next-actions
  {:test (fn []
           (is (= (next-actions {"A" #{"G"} "B" #{"C" "F"} "F" #{}}) ["F"]))
           (is (= (next-actions {"A" #{} "B" #{"C" "F"} "F" #{}}) ["A" "F"]))
           (is (= (next-actions {"Z" #{} "B" #{"C" "F"} "F" #{}}) ["F" "Z"]))
           (is (= (next-actions (dependency-map test-input)) ["C"]))
           )}
  [dependency-map]
  (->> dependency-map
       (filter #(empty? (val %)))
       (sort-by key)
       (map #(first %))
       (vec)))

(defn do-action
  {:test (fn []
           (is (= (do-action {"A" #{} "B" #{"A" "F"} "F" #{"A"}} "A")
                  {"B" #{"F"} "F" #{}})))}
  [dependency-map action]
  (as-> dependency-map $
        (keys $)
        (reduce (fn [current-map key]
                  (update current-map key (fn [current-deps]
                                            (difference current-deps #{action})))) dependency-map $)
        (dissoc $ action)))

(defn order
  {:test (fn []
           (is (= (order test-input) "CABDFE"))
           (is (= (order input) "GKPTSLUXBIJMNCADFOVHEWYQRZ")))}
  [raw]
  (let [processed (dependency-map raw)]
    (loop [current-action (next-action processed)
           actions-todo processed
           executed ""]
      (if (empty? actions-todo)
        executed
        (recur
          (next-action (do-action actions-todo current-action))
          (do-action actions-todo current-action)
          (str executed current-action))))))

(defn time-to-complete-letter
  {:test (fn [] (is (= (time-to-complete-letter "A") 0)))}
  [letter]
  (- (dec (int (first letter))) 4))

(defn get-finished-tasks
  {:test (fn []
           (is (= (get-finished-tasks [nil {:task "A" :time 0} {:task "B" :time 1}]) #{"B"}))
           )}
  [working-on]
  (->> working-on
       (filter (fn [task]
                 (if (not (nil? task))
                   (= (:time task) (time-to-complete-letter (:task task)))
                   nil)))
       (map :task)
       (set)))

(defn update-workers
  {:test (fn []
           (is (= (update-workers [nil] #{} ["A"]) [{:task "A" :time 0}]))
           (is (= (update-workers [nil {:task "A" :time 0}] #{} []) [nil {:task "A" :time 1}]))
           (is (= (update-workers [{:task "A" :time -1}] #{"A"} ["B"]) [{:task "B" :time 0}]))
           (is (= (update-workers [nil {:task "A" :time 0} {:task "B" :time 62}] #{"B"} ["C" "D"]) [{:task "C" :time 0} {:task "A" :time 1} {:task "D" :time 0}]))
           (is (= (update-workers [{:task "A" :time -1} nil] #{"A"} ["B" "C"]) [{:task "B" :time 0} {:task "C" :time 0}]))
           (is (= (update-workers [{:task "A" :time -1} nil nil] #{"A"} ["B" "C"]) [{:task "B" :time 0} {:task "C" :time 0} nil]))
           (is (= (update-workers [{:task "A" :time -1} nil nil] #{"A"} ["B" "C" "D"]) [{:task "B" :time 0} {:task "C" :time 0} {:task "D" :time 0}]))
           (is (= (update-workers [{:task "A" :time 2} {:task "B" :time 3}] #{} ["C"]) [{:task "A" :time 3} {:task "B" :time 4}]))
           )}
  [workers finished available]
  (as-> workers $
        (map (fn [worker]
               (if (contains? finished (:task worker))
                 nil
                 worker)) $)
        (loop [new-workers []
               inner-workers $
               inner-available available]
          (if (= (count new-workers) (count workers))
            new-workers
            (recur
              (cond
                (and (nil? (first inner-workers)) (not (empty? inner-available)))
                (conj new-workers {:task (first inner-available)
                                   :time 0})
                (and (nil? (first inner-workers)) (empty? inner-available))
                (conj new-workers nil)
                (not (nil? (first inner-workers)))
                (conj new-workers (update (first inner-workers) :time inc)))
              (vec (next inner-workers))
              (vec (if (and (nil? (first inner-workers)) (not (empty? inner-available)))
                     (next inner-available)
                     inner-available)))))))

(defn update-work
  {:test (fn []
           (is (= (update-work {:workers      [nil {:task "A" :time -1} {:task "B" :time 1}]
                                :actions-todo {"A" #{} "B" #{} "C" #{"B"} "D" #{"B"}}})
                  {:workers      [{:task "C" :time 0} {:task "A" :time 0} {:task "D" :time 0}]
                   :actions-todo {"A" #{} "C" #{} "D" #{}}}))
           )}
  [{workers :workers actions-todo :actions-todo}]
  (let [finished (get-finished-tasks workers)
        in-progress (set (remove nil? (map :task workers)))
        updated-todo (->> finished
                          (reduce (fn [current-todo action]
                                    (do-action current-todo action)) actions-todo))
        available-actions (remove #(contains? in-progress %) (next-actions updated-todo))
        updated-workers (update-workers workers finished available-actions)]
    {:workers      updated-workers
     :actions-todo updated-todo}))

(defn timer
  {:test (fn []
           (is (= (timer test-input 2) 15))
           (is (= (timer input 5) 920))
           )}
  [raw number-of-workers]
  (loop [actions-todo (dependency-map raw)
         workers (vec (repeat number-of-workers nil))
         tick 0]
    (let [updated-work (update-work {:workers      workers
                                     :actions-todo actions-todo})
          updated-actions-todo (:actions-todo updated-work)]
      (identity updated-work)
      (if (empty? updated-actions-todo)
        tick
        (recur
          updated-actions-todo
          (:workers updated-work)
          (inc tick))))))

(clojure.tools.trace/trace (timer input 5))
