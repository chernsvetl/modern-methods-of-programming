(ns labs.lab2-2.lab2-2)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

; 0 0.5 1 1.5 2 ... 4.5 ... etc.
; 0 .. 4.5
; map (... f (i in interval) (i in interval + step)
; sum squares
(defn rec-integral [f a b]
  (let [intervals (iterate #(+ % 0.5) a)]
    (->> intervals
         (take-while #(< % b))
         (map #(trapezia-square f % (+ % 0.5)))
         (reduce +)
         )
    )
  )

(defn integral [x]
    (rec-integral  input-f 0 x))

(defn -main [& args]
  (time (integral 100))
  ;(println (integral 5))
  (time (integral 99))
  ;(println (integral 99))
  (time (integral 101))
  (time (integral 100))
  ;(println (integral 101))
  ;(println "-------------------------------")
  )

(-main)
