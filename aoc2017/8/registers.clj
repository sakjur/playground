(def lineparser [:key :op :val :ifkeyword :compkey :compop :compval])
(def comparators {"<" <, ">" >, ">=" >=, "<=" <=, "==" ==, "!=" not=})

(defn split-words [s] (clojure.string/split s #" "))
(defn parse-int [s] (Integer/parseInt s))

(defn val-to-int [line]
  (assoc
    (assoc
      line
      :val
      (parse-int (get line :val)))
    :compval 
    (parse-int (get line :compval))))

(defn run-compare [opcom lcom rcom]
  ((get comparators opcom) lcom rcom))

(defn execute-lineop [op origval amount]
  (if (= op "inc") (+ origval amount) (- origval amount)))

(defn run-line [line state]
  (let [opkey        (get line :key)
        keystate     (get state opkey 0)
        compkey      (get line :compkey)
        compstate    (get state compkey 0)
        compres      (run-compare (get line :compop)
                                  compstate
                                  (get line :compval))]
    (if compres
      (assoc state opkey (execute-lineop (get line :op)
                                         keystate 
                                         (get line :val)))
      state)))

(defn run [state maxval lines]
  (if
    (empty? lines)
    [(apply max (map val state)) maxval]
    (recur (run-line (first lines) state)
           (apply max maxval (map val state))
           (rest lines))))

(->> "input"
     slurp
     clojure.string/split-lines
     (map split-words)
     (map #(zipmap lineparser %))
     (map val-to-int)
     (run {} Double/NEGATIVE_INFINITY)
     println)
