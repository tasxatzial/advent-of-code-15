(ns day03.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(def instructions (slurp input-file))

; --------------------------
; results

(defn -main
  []
  (println instructions))
