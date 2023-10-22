(ns labs.lab2_1.lab2-1)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

(defn rec-integral-no-mem [mem-func f a b]
  (if (>= 0 b)
    0
    (+ (mem-func mem-func f a (- b 0.5)) (trapezia-square f (- b 0.5) b)))
  )

(def rec-integral-mem (memoize rec-integral-no-mem))

(defn integral-no-mem [x]
  (rec-integral-no-mem rec-integral-no-mem input-f 0 x)
  )

(defn integral-mem [x]
  (rec-integral-mem rec-integral-mem input-f 0 x)
  )


(defn -main [& args]
  (time (integral-no-mem 100))
  ;(println (integral-no-mem 100))
  (time (integral-no-mem 99))
  ;(println (integral-no-mem 99))
  (time (integral-no-mem 101))
  (time (integral-no-mem 100))
  ;(println (integral-no-mem 101))
  (println "-------------------------------")
  (time (integral-mem 100))
  ;(println (integral-mem 100))
  (time (integral-mem 99))
  ;(println (integral-mem 99))
  (time (integral-mem 101))
  (time (integral-mem 100))
  ;(println (integral-mem 101))
  )

(-main)


