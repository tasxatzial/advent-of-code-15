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
; problem 2

(defn separate-instructions
  "Splits the instructions into two vectors, first one is for santa,
  second one is for robosanta."
  [instructions]
  (let [split-instructions (partition 2 instructions)
        santa-instructions (mapv first split-instructions)
        robosanta-instructions (mapv second split-instructions)]
    [santa-instructions robosanta-instructions]))

; --------------------------
; results

(defn day03-1
  []
  (count (house-locations instructions)))

(defn day03-2
  []
  (let [[santa-instructions robosanta-instructions] (separate-instructions instructions)
        santa-houses (house-locations santa-instructions)
        robosanta-houses (house-locations robosanta-instructions)]
    (count (into santa-houses robosanta-houses))))

(defn -main
  []
  (println (day03-1))
  (println (day03-2)))
