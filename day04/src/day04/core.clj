(ns day04.core
  (:gen-class))

; md5 function: https://gist.github.com/jizhang/4325757

(import 'java.security.MessageDigest
        'java.math.BigInteger)

(defn md5
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

; --------------------------
; common

(def input "bgvyzdsv")

(defn candidate-strings
  "Returns a lazy seq of the strings that will be examined.
  The first part of the string is always the input.
  The second part is a positive integer (1,2,...)"
  []
  (let [nums (map inc (range))]
    (map #(str input %) nums)))

(defn find-lowest-num
  "Finds the lowest positive number that produces a hash that starts with substr.
  Returns the number as a string."
  [substr]
  (let [strings (candidate-strings)]
    (some #(and (clojure.string/starts-with? (md5 %) substr)
                (apply str (drop (count input) %)))
          strings)))

; --------------------------
; results

(defn day04-1
  []
  (find-lowest-num "00000"))

(defn day04-2
  []
  (find-lowest-num "000000"))

(defn -main
  []
  (println (day04-1))
  (println (day04-2)))
