(ns day01.core
  (:gen-class))

(def input-file "resources\\input.txt")

(def instructions (slurp input-file))

; --------------------------
; problem 1

(defn get-final-floor
  "Returns santa's final floor."
  []
  (let [steps (group-by identity instructions)
        up-count (count (get steps \())
        down-count (count (get steps \)))]
    (- up-count down-count)))

; --------------------------
; problem 2

(defn get-basement-index
  "Returns the position of the first instruction that causes santa to enter
  the basement."
  []
  (loop [[instruction & rest-instructions] instructions
         instruction-index 0
         floor 0]
    (if instruction
      (if (= -1 floor)
        instruction-index
        (if (= \( instruction)
          (recur rest-instructions (inc instruction-index) (inc floor))
          (recur rest-instructions (inc instruction-index) (dec floor))))
      -1)))
; ---------------------------------------
; results

(defn day01-1
  []
  (get-final-floor))

(defn day01-2
  []
  (get-basement-index))

(defn -main
  []
  (println (day01-1))
  (println (day01-2)))
