(ns labs.lab1-3.lab1-3)

(defn my-map [f coll]
  (def empty-list-for-future-elements ())
    (reverse (
               reduce (fn [acc elements] (conj acc (f elements))) empty-list-for-future-elements coll)
               )
    )

(defn my-filter [pred coll]
  (def empty-list-for-future-elements ())
  (reverse
    (reduce (fn [acc elements]
              (if (pred elements)
                (conj acc elements)
                acc
                )
              )
            empty-list-for-future-elements coll)
    )
  )

(println (my-map inc '(1 2 3)))
(println (map inc '(1 2 3)))

(println (my-filter even? '(1 3)))
(println (my-filter even? (range 10)))
(println (filter even?  (range 10)))
