(def divisor 2147483647)
(def sixteen-bits 16rFFFF)

(defn create-generator
  ([skip-value]
   (create-generator skip-value 1))
  ([skip-value dividable-by]
   (fn [value] (let [n (mod (* value skip-value) divisor)]
                 (if
                   (zero? (mod n dividable-by))
                   n
                   (recur n))))))
(defn compare-bits [a b]
  (if
    (= (bit-and a sixteen-bits) (bit-and b sixteen-bits))
    1
    0))

(def gen-a (create-generator 16807 4))
(def gen-b (create-generator 48271 8))

(defn iterator [n curr a b]
  (if
    (zero? n)
    curr
    (let [new-a (gen-a a)
          new-b (gen-b b)]
      (do 
        (recur (dec n) (+ curr (compare-bits new-a new-b)) new-a new-b)))))

(println (iterator 5000000 0 883 879))

