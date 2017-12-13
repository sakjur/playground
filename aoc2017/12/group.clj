(require '[clojure.set])

(defn split-arrows [s] (clojure.string/split s #"<->"))
(defn to-int [s] (Integer/parseInt (clojure.string/trim s)))
(defn difference [a b] (clojure.set/difference a b))

(defn split-children' [pid]
  [(to-int (first pid))
   (set (map to-int (clojure.string/split (second pid) #",")))])

(defn split-children [pids]
  (map split-children' pids))

(defn count-connections [queue visited xs]
  (if
    (empty? queue)
    (count visited)
    (let [head    (first queue)
          tail    (rest queue)
          headval (second (first (filter #(= (first %) head) xs)))
          ]
      (recur
      (apply conj
             tail
             (difference headval visited))
      (conj visited head)
      xs
      ))))

(->> "input"
     slurp
     clojure.string/split-lines
     (map split-arrows)
     split-children
     (count-connections #{0} #{})
     println)
