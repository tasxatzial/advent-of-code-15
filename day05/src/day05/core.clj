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

(defn nice-string_p1?
  "Returns true if the given string is nice, false otherwise (problem 1)"
  [s]
  (and (prop1-p1? s)
       (prop2-p1? s)
       (prop3-p1? s)))

; --------------------------
; problem 2

(defn create-two-letter-substrings
  "Return a vector of all two letter substrings of the given string."
  [s]
  (mapv #(apply str %) (partition 2 1 s)))

(defn find-string
  "Returns true if the collection of strings contains string s, false otherwise."
  [s strings]
  (some #{s} strings))

(defn prop1-p2?
  "Returns true if the given string contains a pair of any two letters that appears
  at least twice in the string without overlapping, else it returns false."
  [s]
  (let [two-letter-substrings (create-two-letter-substrings s)]
    (loop [[substr & rest-substr] two-letter-substrings]
      (if substr
        (if (find-string substr (rest rest-substr))
          true
          (recur rest-substr))
        false))))

(defn prop2-p2?
  "Returns true if the given string contains at least one letter which repeats with
  exactly one letter between them, else it returns false."
  [s]
  (boolean (re-find #"(.).\1" s)))

(defn nice-string_p2?
  "Returns true if the given string is nice, false otherwise (problem 2)"
  [s]
  (and (prop1-p2? s)
       (prop2-p2? s)))

; --------------------------
; results

(defn day05-1
  []
  (->> strings
       (map nice-string_p1?)
       (filter true?)
       (count)))

(defn -main
  []
  (println (day05-1))
  (println (nice-string_p2? "ieodomkazucvgmuy")))
