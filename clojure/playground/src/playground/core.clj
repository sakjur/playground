(ns playground.core)

(declare next-letter)

(defn average
  "Calculates the average of a range of numbers"
  [numbers]
  (if
    (zero? (count numbers))
    0
    (/ (apply + numbers) (count numbers))
    )
  )

(defn average_of_elements
  "Calculates the average of two elements in a vector"
  [numbers e1 e2]
  (average [(get numbers e1) (get numbers e2)])
  )

(defn median
  "Calculates the median of a range of numbers"
  [numbers]
  (let
    [sorted (vec (sort numbers))
     counted (count numbers)
     middle (quot counted 2)]
    (if (zero? counted)
      0 ; avoid division by zero for empty lists
      (if
        (zero? (mod counted 2))
        (average_of_elements sorted middle (dec middle))
        (get sorted middle)
        )
      )
    )
  )

(defn incr-letter [c]
  (if (= c \Z)
    \A 
    (-> c
        (int)
        (inc)
        (char)
        (str)
        )
    )
  )

(defn next-letter'
  [inv]
  (if (nil? inv)
    '(\A)
    (let
      [c'   (incr-letter (first inv))
       cont (next inv)]
      (if (= c' \A)
        (cons c' (next-letter' cont))
        (cons c' cont)
        )
      )
    )
  )

(def skip
  [
  ]
  )

(defn skip-words
  [string]
  (if 
    (= (.indexOf skip string) -1)
    string
    (next-letter string)
    )
  )

(defn next-letter
    ([]
     "A"
    )
    ([string]
    (if (= string "")
      "A"
      (->> string
          (reverse)
          (next-letter')
          (reverse)
          (apply str)
          (skip-words)
          )
      )
    )
  )

(time (median (range 100000)))
