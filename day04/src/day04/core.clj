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

(defn -main
  []
  (println (md5 input)))
