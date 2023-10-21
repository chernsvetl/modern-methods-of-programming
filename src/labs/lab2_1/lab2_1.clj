(ns labs.lab2_1.lab2-1)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

(defn rec-integral-no-mem [f a b]
  (if (>= 0 b)
    0
    (+ (rec-integral-no-mem f a (- b 1)) (trapezia-square f (- b 1) b)))
  )

(defn integral-no-mem [x]
  (rec-integral-no-mem input-f 0 x))

(def integral-mem (memoize integral-no-mem))


(defn -main [& args]
  (time (integral-no-mem 100))
  ;(println (integral-no-mem 100))
  (time (integral-no-mem 99))
  ;(println (integral-no-mem 99))
  (time (integral-no-mem 101))
  ;(println (integral-no-mem 101))
  (time (integral-no-mem 100))
  ;(println (integral-no-mem 100))
  (println "-------------------------------")
  (time (integral-mem 100))
  ;(println (integral-no-mem 100))
  (time (integral-mem 99))
  ;(println (integral-no-mem 99))
  (time (integral-mem 101))
  ;(println (integral-no-mem 101))
  (time (integral-mem 100))
  ;(println (integral-no-mem 100))
  )

(-main)



