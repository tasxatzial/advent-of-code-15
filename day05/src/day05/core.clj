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

; --------------------------
; results

(defn -main
  []
  (println strings))
