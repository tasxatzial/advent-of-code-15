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
; results

(defn -main
  []
  (println gift-dimensions))
