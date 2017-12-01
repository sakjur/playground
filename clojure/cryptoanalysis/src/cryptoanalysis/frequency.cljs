(ns cryptoanalysis.frequency
  (:require [cryptoanalysis.common :as common :refer [plaintext]]))

(defn count-letter-frequency [result string]
  (if (empty? string)
    result
    (let
      [letter (first string)
       amount (count (filter #(= % letter) string))
       new-string (filter #(not (= % letter)) string)]
      (recur (merge result {letter amount}) new-string)
      )
    )
  )

(defn format-freq [e] [:span {:class "value"} [:strong (key e)] ": " (val e) "; "])

(defn letter-frequency []
  (->> @plaintext
       (map common/to-uppercase)
       (filter common/is-letter?)
       (count-letter-frequency {})
       (sort-by #(val %))
       reverse
  ))

(defn render-frequency-table [n absolute]
  (let [percentage (map (fn [c] [(key c)
                                 (common/to-percent (/ (val c) n))]) absolute)]
   [:div {:class "part"}
    [:h3 "Frekvenstabell"]
    [:p (map format-freq percentage)]
    [:hr]
    [:p (map format-freq absolute)]
    ]
  ))

(defn render-encrypted [shuffled]
  (let [crypto-key (common/zip common/alphabet-uppercase shuffled)]
   [:div {:class "part"}
    [:h3 "Kryptotext"]
    (apply str (map #(common/encrypt-character crypto-key %) @plaintext))
    ]))

(defn render-key [shuffled]
   [:div {:class "part"}
    [:h3 "Nyckel"]
    [:strong common/alphabet-uppercase]
    [:hr]
    [:strong (apply str shuffled)]
    ]
  )

(defn render []
  (let
    [letters (letter-frequency)
     shuffled-letters (shuffle common/alphabet-uppercase)
     letter-total (reduce + (map #(val %) letters))]
  [:div
   [:p letter-total " bokstäver. "
    (count letters) " unika bokstäver."]
   [render-frequency-table letter-total letters]
   [render-encrypted shuffled-letters]
   [render-key shuffled-letters]
   ]
  ))

