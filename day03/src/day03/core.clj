(ns day03.core
  (:gen-class))

; --------------------------
; common

(def input-file "resources\\input.txt")

(def memoized-input-file->instructions (fn [] (slurp input-file)))

; --------------------------
; problem 1

(defn get-next-house-location
  "Finds the location of the next house based on the current location [x y]
  and an instruction char (^ > v <). Returns a vector."
  [[x y] instruction]
  (case instruction
    \^ [x (inc y)]
    \v [x (dec y)]
    \> [(inc x) y]
    \< [(dec x) y]))

(defn get-all-visited-houses
  "Parses the input string and returns the locations of all houses that will
  be visited at least once. Returns a set of vectors, each vector represents
  a location."
  [instructions]
  (loop [[instruction & rest-instructions] instructions
         curr-location [0 0]
         house-locations #{curr-location}]
    (if instruction
      (let [new-location (get-next-house-location curr-location instruction)]
        (if (contains? house-locations new-location)
          (recur rest-instructions new-location house-locations)
          (recur rest-instructions new-location (conj house-locations new-location))))
      house-locations)))

; --------------------------
; problem 2

(defn separate-instructions
  "Splits the input string into two vectors as shown in the description.
  First vector is for santa, the second one is for robosanta."
  [instructions]
  (let [split-instructions (partition 2 instructions)
        santa-instructions (mapv first split-instructions)
        robosanta-instructions (mapv second split-instructions)]
    [santa-instructions robosanta-instructions]))

; --------------------------
; results

(defn day03-1
  []
  (-> (memoized-input-file->instructions)
      get-all-visited-houses
      count))

(defn day03-2
  []
  (let [[santa-instructions robosanta-instructions] (separate-instructions (memoized-input-file->instructions))
        santa-houses (get-all-visited-houses santa-instructions)
        robosanta-houses (get-all-visited-houses robosanta-instructions)]
    (count (into santa-houses robosanta-houses))))

(defn -main
  []
  (println (day03-1))
  (println (day03-2)))
