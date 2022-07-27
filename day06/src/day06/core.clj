(ns day06.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn parse-loc
  "Accepts a collection of strings and maps each element to an integer. Returns a vector."
  [loc]
  (mapv #(Integer/parseInt %) loc))

(defn parse-line
  "Parses an input line and returns a vector that consists of:
  a keyword (:on :off :toggle) and two vectors that correspond to the
  opposite corners of a rectangle."
  [s]
  (let [replaced-s (-> s
                       (clojure.string/replace #"turn on " "~on~")
                       (clojure.string/replace #"turn off " "~off~")
                       (clojure.string/replace #"toggle " "~toggle~")
                       (clojure.string/split #"~| through "))
        [instr loc1 loc2] (->> replaced-s
                               (filter (complement clojure.string/blank?))
                               (map #(clojure.string/split % #",")))
        instr-key (keyword (first instr))]
    (vector instr-key (parse-loc loc1) (parse-loc loc2))))

(defn parse
  "Parses the input and returns a vector of instructions.
  An instruction is represented by the data structure returned by parse-line."
  [s]
  (mapv parse-line (clojure.string/split-lines s)))

(def instructions (parse (slurp input-file)))

(defn is-included?
  "Returns true if [x0 y0] is contained in the rectangle specified by
  the top-left corner [x1 y1] and bottom-right corner [x2 y2], else it returns false."
  [[x0 y0] [x1 y1] [x2 y2]]
  (and (>= x2 x0 x1)
       (>= y2 y0 y1)))

(defn final-state
  "Computes the final state of a light positioned at loc. New-state-fn is the
  function that computes its next state."
  [loc initial-state new-state-fn]
  (loop [state initial-state
         [instruction & rest-instructions] instructions]
    (if instruction
      (let [next-state (new-state-fn state loc instruction)]
        (recur next-state rest-instructions))
      state)))

(defn grid
  "Returns a 1000x1000 grid. Coordinates start from [0 0] and go up to [999 999]."
  []
  (for [x (range 1000)
        y (range 1000)]
    [x y]))

(def memoized-grid (memoize grid))

; --------------------------
; results

(defn -main
  []
  (println (day06-1))
  (println (day06-2)))
