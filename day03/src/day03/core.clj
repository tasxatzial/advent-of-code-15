(ns day03.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(def instructions (slurp input-file))

(defn next-house-location
  "Finds the location of the next house based on the current location [x y]
  and an instruction (^ > v <)."
  [[x y] instruction]
  (case instruction
    \^ [x (inc y)]
    \v [x (dec y)]
    \> [(inc x) y]
    \< [(dec x) y]))

(defn house-locations
  "Finds the locations of all houses that receive at least one present."
  [instructions]
  (loop [[instruction & rest-instructions] instructions
         curr-location [0 0]
         house-locations #{curr-location}]
    (if instruction
      (let [new-location (next-house-location curr-location instruction)]
        (if (contains? house-locations new-location)
          (recur rest-instructions new-location house-locations)
          (recur rest-instructions new-location (conj house-locations new-location))))
      house-locations)))

; --------------------------
; results

(defn -main
  []
  (println (house-locations instructions)))
