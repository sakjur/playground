(defn delta [a b] (Math/abs (- a b)))

(defn layer_of
  ([n layer]
   (if (> (* layer layer) n)
     layer
     (recur n (+ layer 2))))
  ([n] (layer_of n 1)))

(defn nodes_in_layer [layer] (- (* layer 4) 4))
(defn corner_to_center [layer] (/ (nodes_in_layer layer) 8))

(defn centers [layer]
  (map #(- (* layer layer) (* % (corner_to_center layer))) [1 3 5 7]))

(defn node_to_center [n layer] (apply min (map #(delta n %) (centers layer))))
(defn layer_to_origo [layer] (/ (dec layer) 2))

(defn calculate_distance [n]
  (let [layer (layer_of n)]
    (+ (layer_to_origo layer) (node_to_center n layer))))

(println (calculate_distance 265149))
