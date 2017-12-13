(defn skip-garbage [xs cnt]
  (case (first xs)
    \! (recur (rest (rest xs)) cnt)
    \> [(rest xs) cnt]
    (recur (rest xs) (inc cnt))
  ))

(defn parse [xs depth scores garbage]
  (case (first xs)
    nil [(apply + scores) garbage]
    \! (recur (rest (rest xs)) depth scores garbage)
    \{ (recur (rest xs) (inc depth) scores garbage)
    \} (recur (rest xs) (dec depth) (cons depth scores) garbage)
    \< (let [skipped (skip-garbage (rest xs) 0)]
         (recur (first skipped) depth scores (+ garbage (second skipped))))
    (recur (rest xs) depth scores garbage)
    ))

(-> "input"
    slurp
    (parse 0 [] 0)
    println)
