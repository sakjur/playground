(defn not_empty? [o] (not (empty? o)))
(defn parseInt [s] (Integer/parseInt s))
(defn splitStr [re s] (clojure.string/split s re))
(defn line_delta [line] (- (last line) (first line)))

(defn checksum_line [line]
  (->> line
    (splitStr #" ")
    (filter not_empty?)
    (map parseInt)
    sort
    line_delta
    )
  )

(->> "input"
     slurp
     (splitStr #"\n")
     (map checksum_line)
     (reduce +)
     println)
