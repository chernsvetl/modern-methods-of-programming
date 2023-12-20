(ns labs.lab3-1.lab3-1)

(defn main-func [n]
  (= 0 (mod n 2))
  )

(defn heavy-func [f]
  (fn [coll]
    (Thread/sleep 100)
    (f coll)
    )
  )


(defn my-partition [coll cnt-threads]
  (loop [cur-coll coll, part-coll (int (/ (count coll) cnt-threads)), res []]
    (if (> (count cur-coll) 0)
      (recur (drop part-coll cur-coll) part-coll (conj res (take part-coll cur-coll)))
      res
      )
    )
  )


(defn parallel-filter [f coll cnt-threads]
  (let [heavy-f (heavy-func f)]
    (->>
      (my-partition coll cnt-threads)
      (map #(future (doall(filter heavy-f %))))
      (doall)
      (mapcat deref)
      )
    )
  )


(defn -main []
  (time (doall (filter (heavy-func main-func) (range 10))))
  (time (doall (parallel-filter (heavy-func main-func) (range 10) 1)))
  (time (doall (parallel-filter (heavy-func main-func) (range 10) 2)))
  (time (doall (parallel-filter (heavy-func main-func) (range 10) 4)))
  )

(println (-main))