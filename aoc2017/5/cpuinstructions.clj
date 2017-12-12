(defn parse-int [s] (Integer/parseInt s))
(defn mapify [xs] (zipmap (range (count xs)) xs))

(defn run [step pos is]
  (let [offset (nth is pos nil)]
    (if (nil? offset)
      step
      (recur (inc step)
             (+ pos offset)
             (assoc is pos (if (< 2 offset) (dec offset) (inc offset)))))))

(->> "instructions"
     slurp
     clojure.string/split-lines
     (map parse-int)
     (apply vector)
     (run 0 0)
     println)
