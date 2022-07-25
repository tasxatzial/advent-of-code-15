(ns day01.core
  (:gen-class))

(def input-file "resources\\input.txt")

(def instructions (slurp input-file))

; --------------------------
; problem 1

(defn find-final-floor
  "Finds santa's final floor"
  []
  (let [steps (group-by identity instructions)
        up-count (count (get steps \())
        down-count (count (get steps \)))]
    (- up-count down-count)))

; ---------------------------------------
; results

(defn day01-1
  []
  (find-final-floor))

(defn -main
  []
  (println (day01-1)))
