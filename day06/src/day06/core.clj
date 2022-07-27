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

(defn grid
  "Returns a 1000x1000 grid. Coordinates start from [0 0] and go up to [999 999]."
  []
  (for [x (range 1000)
        y (range 1000)]
    [x y]))

(def memoized-grid (memoize grid))

; --------------------------
; problem 1

(def reversed-instructions (rseq instructions))

(defn compute-state_p1
  "Finds the final state of a light given its initial state and the number
  of times its state has been toggled (problem 1)."
  [initial-state toggle-count]
  (if (even? toggle-count)
    initial-state
    (if (= :on initial-state)
      :off
      :on)))

(defn final-state_p1
  "Returns the final state of a light positioned at loc (problem 1)"
  [loc]
  (loop [[[cmd loc1 loc2] & rest-instructions] reversed-instructions
         toggle-count 0]
    (if cmd
      (if (is-included? loc loc1 loc2)
        (case cmd
          :on (compute-state_p1 :on toggle-count)
          :off (compute-state_p1 :off toggle-count)
          :toggle (recur rest-instructions (inc toggle-count)))
        (recur rest-instructions toggle-count))
      (compute-state_p1 :off toggle-count))))

; --------------------------
; problem 2

(defn compute-state_p2
  "Returns the new state of a light positioned at loc when it receives an instruction of
  the form [cmd loc1 loc2] (problem 2)"
  [state loc [cmd loc1 loc2]]
  (if (is-included? loc loc1 loc2)
    (case cmd
      :toggle (+ state 2)
      :on (inc state)
      :off (max (dec state) 0))
    state))

(defn final-state_p2
  "Computes the final state of a light positioned at loc (problem 2)"
  [loc initial-state]
  (loop [state initial-state
         [instruction & rest-instructions] instructions]
    (if instruction
      (let [next-state (compute-state_p2 state loc instruction)]
        (recur next-state rest-instructions))
      state)))

; --------------------------
; results

(defn day06-1
  []
  (->> (memoized-grid)
       (map #(final-state_p1 %))
       (filter #{:on})
       count))

(defn day06-2
  []
  (->> (memoized-grid)
       (map #(final-state_p2 % 0))
       (apply +)))

(defn -main
  []
  (println (time (day06-1)))
  (println (time (day06-2))))
