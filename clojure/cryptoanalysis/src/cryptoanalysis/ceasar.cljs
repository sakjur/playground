(ns cryptoanalysis.ceasar
  (:require [cryptoanalysis.common :as common]
            [reagent.core :as reagent :refer [atom]]))

(defonce rotn (atom 13))
(defonce vigenere (atom "enigma"))

(defn make-key [n]
  (str (apply str (drop n common/alphabet-uppercase))
       (apply str (take n common/alphabet-uppercase))))

(defn update-rot [input]
  (reset! rotn
          (-> input
              .-target
              .-value
              int
              (mod (count common/alphabet-uppercase)))
  ))

(defn encrypt []
  (let [crypto-key (common/zip common/alphabet-uppercase (make-key @rotn))]
    (map #(common/encrypt-character crypto-key %) @common/plaintext)
    )
  )

(defn vigenere-make-key [character]
  (let [rot-vec (common/letter-pos character)]
    (make-key rot-vec)
  ))

(defn make-vigenere-table []
  (let [make-key #(common/zip common/alphabet-uppercase (vigenere-make-key %))
        sorted-vigenere (->> @vigenere sort distinct (map common/to-uppercase))]
  (common/zip sorted-vigenere (map make-key sorted-vigenere))))

(defn vigenere-encrypt []
  (let [codelen (count @common/plaintext)
        padding (->> @vigenere
                     (repeat (+ (/ codelen (count @vigenere)) 1))
                     (apply str)
                     (take codelen)
                     (map common/to-uppercase)
                     (apply str))
        table   (make-vigenere-table)]
      (apply str (mapv
                   (fn [pad c] (common/encrypt-character (get table pad) c))
                   padding
                   @common/plaintext))
    ))

(defn render-cryptotext []
  [:div {:class "part"}
   [:h3 "Kryptotext"]
   [:p (encrypt)]]
  )

(defn render-vigenere-cryptotext []
  [:div {:class "part"}
   [:h3 "Vigenèrekryptotext"]
   [:p (str (vigenere-encrypt))]]
  )

(defn render-setup []
  [:div {:class "part"}
   [:h3 "Inställningar"]
   [:input {:type "number" :value @rotn :on-change update-rot}]
   [:input {:value @vigenere
            :on-change #(reset! vigenere (-> % .-target .-value))}]
   ]
  )

(defn render-key []
  [:div {:class "part"}
   [:h3 "Nyckel -- rot" @rotn]
   [:strong common/alphabet-uppercase]
   [:hr]
   [:strong (make-key @rotn)]
  ]
  )

(defn render []
  [:div
   [render-cryptotext]
   [render-vigenere-cryptotext]
   [render-setup]
   [render-key]
   ]
  )
