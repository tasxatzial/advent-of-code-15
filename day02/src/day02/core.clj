(ns day02.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn parse-line
  "Parses an input line and returns a vector of 3 integers
  that denote the gift dimensions."
  [s]
  (let [split-s (clojure.string/split s #"x")]
    (mapv #(Integer/parseInt %) split-s)))

(defn parse-file
  "Reads and parses the input file into a vector of vectors. Each vector
  holds the gift dimensions as returned by parse-line."
  []
  (->> input-file
       slurp
       clojure.string/split-lines
       (mapv parse-line)))

(def memoized-input-file->dimensions (memoize parse-file))

; --------------------------
; problem 1

(defn get-gift-paper
  "Given a gift (a vector of 3 numbers), it returns the area of the
  wrapping paper."
  [gift]
  (let [l (get gift 0)
        w (get gift 1)
        h (get gift 2)
        lw (* l w)
        lh (* l h)
        wh (* w h)
        min-area (min lw lh wh)]
    (+ min-area (* 2 (+ lw lh wh)))))

(defn get-gifts-total-paper
  "Returns the total area of the wrapping paper required by all gifts."
  [gift-dimensions]
  (->> gift-dimensions
       (mapv get-gift-paper)
       (reduce +)))

; --------------------------
; problem 2

(defn get-gift-ribbon
  "Given a gift (a vector of 3 numbers), it returns the length of the
  wrapping ribbon."
  [gift]
  (let [l (get gift 0)
        w (get gift 1)
        h (get gift 2)
        lw (* 2 (+ l w))
        lh (* 2 (+ l h))
        wh (* 2 (+ w h))
        min-perimeter (min lw lh wh)
        area (* l w h)]
    (+ min-perimeter area)))

(defn get-gifts-total-ribbon
  "Returns the total length of the wrapping ribbon required by all gifts."
  [gift-dimensions]
  (->> gift-dimensions
       (mapv get-gift-ribbon)
       (reduce +)))

; --------------------------
; results

(defn day02-1
  []
  (get-gifts-total-paper (memoized-input-file->dimensions)))

(defn day02-2
  []
  (get-gifts-total-ribbon (memoized-input-file->dimensions)))

(defn -main
  []
  (println (day02-1))
  (println (day02-2)))
