;;
;; ADVENT OF CODE 2017
;; Day 1
;;
;; -*- Inverse Captcha -*-
;; By: Emil Tullstedt (@sakjur)
;;

(defn inverse_captcha [shifted li]
  (->> li
       (map vector shifted)
       (filter #(= (first %1) (second %1)))
       (map first)
       (reduce +)
  ))

(defn drop_half_list [li] (drop (/ (count li) 2) li))

(defn inverse_captchas [li]
  (do
    (println "Task 1:"
             (inverse_captcha (concat (rest li) [(first li)]) li))
    (println "Task 2:"
             (inverse_captcha (concat (drop_half_list li) li) li))
    )
  )

(->> "input"
     slurp
     clojure.string/trim
     seq
     (map int)
     (map #(- % 48)) ; ASCII to integer
     inverse_captchas)
