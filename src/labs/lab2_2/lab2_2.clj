(ns labs.lab2-2.lab2-2)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

(def seq (map second
           (iterate
             (fn [[k v]] [(+ k 0.5) (+ v (trapezia-square input-f k (+ k 0.5)))])
                              [0 0]
                       ))
  )

(defn rec-integral [f n]
  (nth seq n)
  )

(defn integral [x]
  (rec-integral input-f (quot x 0.5)))


(defn -main [& args]
  (time (integral 100))
  ;(println (integral 100))
  (time (integral 99))
  ;(println (integral 99))
  (time (integral 101))
  ;(println (integral 101))
  (time (integral 100))
  ;(println (integral 100))
  )

(-main)
