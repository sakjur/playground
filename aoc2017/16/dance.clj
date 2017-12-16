(def short-alphabet "abcde")
(def long-alphabet "abcdefghijklmnop")

(defn split-commas [text] (clojure.string/split text #","))
(defn to-int [c] (if (nil? c) nil (Integer/parseInt c)))

(defn swap-two [li] (concat (second li) (first li)))
(defn swap-by-name [state a b]
  (let [a'  (.indexOf state a)
        b'  (.indexOf state b)]
      (assoc state a' b b' a)))
(defn swap-by-pos [state a b] (assoc state a (nth state b) b (nth state a)))

(defn parse-move [instr]
  (let [clean   (clojure.string/trim instr)
        move    (first clean)
        [a b]   (clojure.string/split (apply str (rest clean)) #"/")]
    (if
      (= move \p)
      [move a b]
      [move (to-int a) (to-int b)]
  )))

(defn dance-move [state [move a b]]
  (case move
    \s  (vec (swap-two (split-at (- (count state) a) state)))
    \p  (swap-by-name state a b)
    \x  (swap-by-pos state a b)))

(defn dance [state instructions]
  (if (empty? instructions)
    state
    (recur (dance-move state (first instructions)) (rest instructions))))

(defn find-cycle
  ([state instructions]
   (find-cycle (dance state instructions) state 1 instructions))
  ([state orig n instructions]
   (if (= state orig)
     n
     (recur (dance state instructions) orig (inc n) instructions))))

(defn dances' [state iterations instructions]
  (if
    (zero? iterations)
    state
    (recur (dance state instructions) (dec iterations) instructions)))

(defn dances [state iterations instructions] 
  (let [cycles   (find-cycle state instructions)
        new-iter (mod iterations cycles)]
    (dances' state new-iter instructions)))

(->> "input"
     slurp
     split-commas
     (map parse-move)
     (dances (vec (map str long-alphabet)) 1000000000)
     clojure.string/join
     println)

