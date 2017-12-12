(defn split-on-tabs [s] (clojure.string/split s #"\t"))
(defn parse-int [s] (Integer/parseInt s))
(defn teeprintln [s] (do (println s) s))

(defn dostep [n pos xs]
  (let [realpos (mod pos (count xs))]
    (if
      (zero? n)
      xs
      (recur (dec n)
             (inc realpos)
             (assoc xs realpos (inc (nth xs realpos)))))))

(defn reallocate [step prev xs]
  (let [maxval (apply max xs)
        maxpos (.indexOf xs maxval)]
    (if 
      (= -1 (.indexOf prev xs))
      (recur (inc step)
             (cons xs prev)
             (dostep maxval (inc maxpos) (assoc xs maxpos 0)))
      [step (- step (.indexOf (reverse prev) xs))]
      )
    )
  )

(->> "banks"
     slurp
     split-on-tabs
     (map clojure.string/trim)
     (map parse-int)
     (apply vector)
     (reallocate 0 [])
     println)

