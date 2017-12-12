(defn has_duplicates? [xs forbid_anagrams]
  (= (count xs)
     (count (set (if forbid_anagrams (map set xs) xs)))))

(defn unique_words [string]
  (-> string
      (clojure.string/split #" ")
      (has_duplicates? true)))

(->> "input_passphrases"
     slurp
     clojure.string/split-lines
     (filter unique_words)
     count
     println)
