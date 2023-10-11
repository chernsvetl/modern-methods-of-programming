(ns labs.lab2_1.lab2-1)


(defn trapezia-area [func a b n]  (/(* (/ (+ (func a) (func b)) 2) (- b a))n))

(defn integral [f step]
  (fn [x]
    (if (< x 0)
      0
      (trapezia-area f 0 x (int (/ x step))))))

(defn integral-memoized [f step]
  (let [memoized-trapezia (memoize trapezia-area)]
    (fn [x]
      (if (< x 0)
        0
        (memoized-trapezia f 0 x (int (/ x step)))))))


(def f-x #(* % %))
(def my-fn (integral f-x 5))
(def my-fn-memoized (integral-memoized f-x 5))

(println(my-fn 10.0))

(time (my-fn 100))
(time (my-fn 99))
(time (my-fn 101))
(time (my-fn 100))
(println "---------------")
;(println(my-fn-memoized 1))
(time(my-fn-memoized 100))
(time(my-fn-memoized 99))
(time(my-fn-memoized 101))
(time(my-fn-memoized 100))