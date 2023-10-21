(ns labs.lab2-2.lab2-2)

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

(defn integral-mem [x]
  (rec-integral-mem rec-integral-mem input-f 0 x)
  )


(defn -main [& args]
  (time (integral-mem 100))
  ;(println (integral-mem 100))
  (time (integral-mem 99))
  ;(println (integral-mem 99))
  (time (integral-mem 101))
  ;(println (integral-mem 101))
  )

(-main)