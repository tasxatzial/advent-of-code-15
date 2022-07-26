(ns day02.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn parse-line
  "Parse an input line and return a vector of 3 integers
  that denote the gift dimensions."
  [s]
  (let [split-s (clojure.string/split s #"x")]
    (mapv #(Integer/parseInt %) split-s)))

(defn parse
  "Parse the input string and return a vector of vectors. Each vector
  holds the gift dimensions as returned by parse-line."
  [s]
  (let [split-s (clojure.string/split-lines s)]
    (mapv parse-line split-s)))

(def gift-dimensions (parse (slurp input-file)))

; --------------------------
; problem 1

(defn find-gift-paper
  "Returns the total area of the paper required for the given gift.
  Gift is a vector of 3 numbers."
  [gift]
  (let [l (get gift 0)
        w (get gift 1)
        h (get gift 2)
        lw (* l w)
        lh (* l h)
        wh (* w h)
        min-area (min lw lh wh)]
    (+ min-area (* 2 (+ lw lh wh)))))

; --------------------------
; results

(defn -main
  []
  (println (find-gift-paper [1 1 10])))
