(ns labs.lab3-2.lab3-2)


(defn main-func [n]
  (= 0 (mod n 2))
  )


(defn heavy-func [f]
  (fn [coll]
    (Thread/sleep 100)
    (f coll)
    )
  )


(defn my-partition [block-size coll]
  (take-while
    (partial seq)
    (->>
      (iterate (fn [[block tail]]
                 [(take block-size tail) (drop block-size tail)])
               [(take block-size coll) (drop block-size coll)])
      (map #(first %))
      )
    )
  )


(defn lazy-parallel-filter [pred coll block-size cnt-threads]
  (->>
    (my-partition block-size coll)
    (map #(future (doall (filter pred %))))
    (my-partition cnt-threads)
    (map #(future (mapcat deref (doall %))))
    (mapcat deref)
    )
  )


(defn -main []
  (time (doall (filter (heavy-func main-func) (range 100))))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 1)))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 2)))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 4)))

  (time (doall (take 100 (filter (heavy-func main-func) (iterate inc 1)))))
  (time (doall (take 100 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 1))))
  (time (doall (take 100 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 2))))
  (time (doall (take 100 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 4))))
  )


(println (-main))