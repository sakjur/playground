(ns cryptoanalysis.core
    (:require [cryptoanalysis.frequency :as frequency]
              [cryptoanalysis.ceasar :as ceasar]
              [reagent.core :as reagent :refer [atom]]
              [cryptoanalysis.common :refer [plaintext]]))

(enable-console-print!)

(defonce page (atom :freq))

(defn navigation-bar []
  [:nav
   [:a {:href "#" :on-click #(reset! page :freq)} "Frekvensanalys"]
   [:a {:href "#" :on-click #(reset! page :ceasar)} "Ceasarchiffer"]
   ]
  )

(def pages {:freq     [frequency/render]
            :ceasar   [ceasar/render]})

(defn render-page []
  [:div {:id "container"}
   [:h1 "Kryptografi & sånt"]
   [:textarea {:value @plaintext
               :on-change #(reset! plaintext (-> % .-target .-value))
               :rows 5
               :cols 80}]
   (navigation-bar)
   [get pages @page]])

(reagent/render-component [render-page]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
)
