(ns day05.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn parse
  "Splits the input string by \n and converts it into a vector of strings."
  [s]
  (clojure.string/split-lines s))

(def strings (parse (slurp input-file)))

(defn is-vowel?
  [c]
  (or (= c \a) (= c \e) (= c \i) (= c \o) (= c \u)))

; --------------------------
; problem 1

(defn prop1-p1?
  "Returns true if the given string has at least 3 vowels, false otherwise."
  [s]
  (>= (count (filter is-vowel? s)) 3))

(defn prop2-p1?
  "Returns true if the given string has least one letter that appears twice in a row,
  false otherwise."
  [s]
  (boolean (re-find #"([a-z])\1" s)))

(defn prop3-p1?
  "Returns true if the given string does not contain ab, cd, pq, xy, false otherwise."
  [s]
  (->> ["ab" "cd" "pq" "xy"]
       (mapv #(re-find (re-pattern %) s))
       (every? nil?)))

(defn nice-string?
  "Returns true if the given string is nice, false otherwise."
  [s]
  (and (prop1-p1? s)
       (prop2-p1? s)
       (prop3-p1? s)))

; --------------------------
; results

(defn -main
  []
  (println (nice-string? "aaa")))
