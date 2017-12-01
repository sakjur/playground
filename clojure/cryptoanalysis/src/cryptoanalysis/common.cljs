(ns cryptoanalysis.common
  (:require [reagent.core :refer [atom]]))

(defonce plaintext (atom (str "Gatsby believed in the green light, the orgastic"
                              " future that year by year recedes before us. It"
                              " eluded us then, but that's no matter--tomorrow"
                              " we will run faster, stretch out our arms"
                              " farther. . . . And one fine morning----\n\n"
                              "So we beat on, boats against the current,"
                              " borne back ceaselessly into the past.")))

(def alphabet-uppercase "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ")
(def alphabet-lowercase "abcdefghijklmnopqrstuvwxyzåäö")

(defn zip [a b] (into (sorted-map) (mapv (fn [x y] [x y]) a b)))

(def alphabet-ltou-map (zip alphabet-lowercase alphabet-uppercase))
(def alphabet-pos (zip alphabet-uppercase (range (count alphabet-uppercase))))

(defn to-uppercase [character]
  (let [uppercase (get alphabet-ltou-map character)]
    (if (nil? uppercase)
      character
      uppercase)))

(defn is-letter? [character]
  (not (empty? (filter #(= % (to-uppercase character)) alphabet-uppercase))))

(defn letter-pos [character] (get alphabet-pos (to-uppercase character)))

(defn to-percent [number]
  (let [v (int (* number 100))]
   (str (if (= v 0) "<1" v) "%"))
  )

(defn encrypt-letter [crypto-key v] (get crypto-key (to-uppercase v))) 

(defn encrypt-character [crypto-key c]
  (let [r (encrypt-letter crypto-key c)]
    (if (nil? r)
      c
      r)))

