(ns day05.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(defn get-input-strings
  "Reads and parses the input file into a vector of strings."
  []
  (-> input-file
      slurp
      clojure.string/split-lines))

(def memoized-get-input-strings (memoize get-input-strings))

(defn is-vowel?
  [c]
  (or (= c \a) (= c \e) (= c \i) (= c \o) (= c \u)))

; --------------------------
; problem 1

(defn p1_has-prop1?
  "Returns true if the given string has at least 3 vowels, false otherwise."
  [s]
  (>= (count (filter is-vowel? s)) 3))

(defn p1_has-prop2?
  "Returns true if the given string has least one letter that appears twice in a row,
  false otherwise."
  [s]
  (boolean (re-find #"([a-z])\1" s)))

(defn p1_has-prop3?
  "Returns true if the given string does not contain ab, cd, pq, xy, false otherwise."
  [s]
  (->> ["ab" "cd" "pq" "xy"]
       (mapv #(re-find (re-pattern %) s))
       (every? nil?)))

(defn p1_nice-string?
  "Returns true if the given string is nice, false otherwise."
  [s]
  (and (p1_has-prop1? s)
       (p1_has-prop2? s)
       (p1_has-prop3? s)))

; --------------------------
; problem 2

(defn create-two-letter-substrings
  "Returns a vector of all two letter substrings of the given string."
  [s]
  (mapv #(apply str %) (partition 2 1 s)))

(defn find-string
  "Returns true if the given collection of strings contains s, nil otherwise."
  [s strings]
  (some #{s} strings))

(defn p2_prop1?
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

(defn p2_prop2?
  "Returns true if the given string contains at least one letter which repeats with
  exactly one letter between them, else it returns false."
  [s]
  (boolean (re-find #"(.).\1" s)))

(defn p2_nice-string?
  "Returns true if the given string is nice, false otherwise."
  [s]
  (and (p2_prop1? s)
       (p2_prop2? s)))

; --------------------------
; results

(defn day05
  [nice-fn?]
  (->> (memoized-get-input-strings)
       (map nice-fn?)
       (filter true?)
       (count)))

(defn -main
  []
  (println (day05 p1_nice-string?))
  (println (day05 p2_nice-string?)))
