(ns day01.core
  (:gen-class))

(def input-file "resources\\input.txt")

(def directions (slurp input-file))

(defn -main
  []
  (println directions))
