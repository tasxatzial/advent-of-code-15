(ns day06.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn parse-loc
  "Parses a location formatted as \"x,y\" into a vector of two numbers."
  [loc]
  (mapv #(Integer/parseInt %) (clojure.string/split loc #",")))

(defn parse-line
  "Parses an input line into a vector that consists of
  a keyword (:on :off :toggle) and two location vectors."
  [s]
  (let [cleared-s (re-seq #"on|off|toggle|\d+,\d+" s)
        loc1 (parse-loc (second cleared-s))
        loc2 (parse-loc (last cleared-s))
        state (keyword (first cleared-s))]
    [state loc1 loc2]))

(defn get-instructions
  "Reads and parses the input file into vector of instructions.
  An instruction is represented by the data structure returned by parse-line."
  []
  (->> input-file
       slurp
       clojure.string/split-lines
       (mapv parse-line)))

(def memoized-instructions (memoize get-instructions))

(defn is-included?
  "Returns true if [x0 y0] is contained in the rectangle specified by
  the top-left corner [x1 y1] and bottom-right corner [x2 y2], else false."
  [[x0 y0] [x1 y1] [x2 y2]]
  (and (>= x2 x0 x1)
       (>= y2 y0 y1)))

(defn grid
  "Returns a 1000x1000 grid with coordinates from [0 0] to [999 999]."
  []
  (for [x (range 1000)
        y (range 1000)]
    [x y]))

(def memoized-grid (memoize grid))

; --------------------------
; problem 1

(def reversed-instructions (rseq (memoized-instructions)))

(defn p1_compute-final-state-when-toggled
  "Returns the final state of a light given its initial state and the number
  of times its state has been toggled."
  [initial-state toggle-count]
  (if (even? toggle-count)
    initial-state
    (if (= :on initial-state)
      :off
      :on)))

(defn p1_compute-final-state
  "Returns the final state of a light positioned at loc."
  [loc]
  (loop [[[cmd loc1 loc2] & rest-instructions] reversed-instructions
         toggle-count 0]
    (if cmd
      (if (is-included? loc loc1 loc2)
        (case cmd
          :on (p1_compute-final-state-when-toggled :on toggle-count)
          :off (p1_compute-final-state-when-toggled :off toggle-count)
          :toggle (recur rest-instructions (inc toggle-count)))
        (recur rest-instructions toggle-count))
      (p1_compute-final-state-when-toggled :off toggle-count))))

; --------------------------
; problem 2

(defn p2_compute-new-state
  "Returns the new state of a light positioned at loc when it receives an
  instruction. An instruction is represented by the data structure returned
  by parse-line."
  [state loc instruction]
  (let [[cmd loc1 loc2] instruction]
    (if (is-included? loc loc1 loc2)
      (case cmd
        :toggle (+ state 2)
        :on (inc state)
        :off (max (dec state) 0))
      state)))

(defn p2_compute-final-state
  "Returns the final state of a light positioned at loc."
  [loc]
  (loop [state 0
         [instruction & rest-instructions] (memoized-instructions)]
    (if instruction
      (-> state
          (p2_compute-new-state loc instruction)
          (recur rest-instructions))
      state)))

; --------------------------
; results

(defn day06-1
  []
  (->> (memoized-grid)
       (map p1_compute-final-state)
       (filter #{:on})
       count))

(defn day06-2
  []
  (->> (memoized-grid)
       (map p2_compute-final-state)
       (reduce +)))

(defn -main
  []
  (println (time (day06-1)))
  (println (time (day06-2))))
