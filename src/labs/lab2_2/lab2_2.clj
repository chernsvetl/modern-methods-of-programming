(ns labs.lab2-2.lab2-2)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

(defn parallel-squares [f a b]
  (let [intervals (range a b 0.5)]
    (->> intervals
      (pmap #(trapezia-square f % (+ % 0.5)))
      (reduce +)
      )
    )
  )


(defn integral-parallel [x]
  (parallel-squares input-f 0 x)
  )


(defn -main [& args]
  (time (integral-parallel 100))
  ;(println (integral-parallel 100))
  (time (integral-parallel 99))
  ;(println (integral-parallel 99))
  (time (integral-parallel 101))
  ;(println (integral-parallel 101))
  (time (integral-parallel 100))
  )

(-main)


