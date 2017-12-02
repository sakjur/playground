;;
;; ADVENT OF CODE 2017
;; Day 2
;;
;; -*- Checksum Divisible -*-
;; By: Emil Tullstedt (@sakjur)
;;

(defn not_empty? [o] (not (empty? o)))
(defn parseInt [s] (Integer/parseInt s))
(defn splitStr [re s] (clojure.string/split s re))
(defn zero_to_nil [n] (if (zero? n) nil n))

(defn evenly_divisible? [a b]
  (if (zero? (mod a b)) (/ a b)
    (if (zero? (mod b a)) (/ b a) nil)
    )
  )

(defn find_evenly_divisible [curr line]
  (if (and (nil? curr) (not_empty? line))
    (recur
      (->> (rest line)
           (map #(evenly_divisible? (first line) %))
           (filter #(not (nil? %)))
           (reduce +)
           zero_to_nil)
      (rest line))
    curr
    )
  )

(defn checksum_line [line]
  (->> line
    (splitStr #" ")
    (filter not_empty?)
    (map parseInt)
    (find_evenly_divisible nil)
    )
  )

(->> "input"
     slurp
     (splitStr #"\n")
     (map checksum_line)
     (reduce +)
     println)
