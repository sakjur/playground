(defn split-tabs [s] (clojure.string/split s #"\t"))
(defn parse-int [s] (Integer/parseInt s))
(defn get-index [x xs]
  (let [index (.indexOf xs x)]
    (if (= index -1) nil index)))

(defn dostep [n pos xs]
  (let [i       (mod pos (count xs))
        newval  (inc (nth xs i))]
    (if
      (zero? n)
      xs
      (recur (dec n)
             (inc i)
             (assoc xs i newval)))))

(defn reallocate [step prev xs]
  (let [maxval (apply max xs)
        maxpos (get-index maxval xs)]
    (if
      (nil? (get-index xs prev))
      (recur (inc step)
             (cons xs prev)
             (dostep maxval
                     (inc maxpos)
                     (assoc xs maxpos 0)))
      [step (- step (get-index xs (reverse prev)))]
      )
    )
  )

(->> "banks"
     slurp
     split-tabs
     (map clojure.string/trim)
     (map parse-int)
     (apply vector)
     (reallocate 0 [])
     println)

