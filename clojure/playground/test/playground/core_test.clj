(ns playground.core-test
  (:require [clojure.test :refer :all]
            [playground.core :refer :all]))

(deftest average-test 
  (testing "Average function"
    (is (= (average []) 0))
    (is (= (average [512 1024 1536]) 1024))
    (is (= (average [1 2 3 4 5]) 3))
    (is (= (average (repeat 5000 50)) 50))
    )
  )

(deftest median-test
  (testing "Median function"
    (is (= (median []) 0))
    (is (= (median [512 1024 1536]) 1024))
    (is (= (median [9 4 0 2 2 6]) 3))
    (is (= (median [12 1 25 4 1000]) 12))
    (is (= (median (repeat 5000 50)) 50))
    )
  )

(deftest next-letter-test
  (testing "Next letter function"
    (is (= (next-letter) "A"))
    (is (= (next-letter "") "A"))
    (is (= (next-letter "A") "B"))
    (is (= (next-letter "Z") "AA"))
    (is (= (next-letter "AZ") "BA"))
    (is (= (next-letter "ZZZ") "AAAA"))
    ; (is (= (next-letter "FUCJ") "FUCL"))
    )
  )
