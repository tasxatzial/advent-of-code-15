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

; --------------------------
; results

(defn -main
  []
  (println instructions))
