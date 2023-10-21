(ns labs.lab2_1.lab2-1)

(defn trapezia-square [f a b]
  (/ (* (+ (f a) (f b)) (- b a)) 2)
  )

(defn input-f [x]
  (* 3 x)
  )

(defn rec-integral-no-mem [f a b]
  (if (>= a b)
    0
    (+ (rec-integral-no-mem f a (- b 1)) (trapezia-square f (- b 1) b)))
  )

(defn integral-no-mem [x]
  (rec-integral-no-mem input-f 0 x))

(println(integral-no-mem 100))

