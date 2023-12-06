(ns labs.lab3-1.lab3-1)

(defn is-even
  [n]
  (= 0 (mod n 2)))

(defn heavy-func
  [n]
  (Thread/sleep 1)
  (is-even n))

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


(defn main []
  (time
    (->> (filter (heavy-func even?) (range 10))
          (doall)
      )
    )
  (println (filter (heavy-func even?) (range 10)))
(println "-------------------------")
  (time
    (->> (parallel-filter (heavy-func even?) (range 10) 1)
         (doall)
         )
    )
  (time
    (->> (parallel-filter (heavy-func even?) (range 10) 3)
         (doall)
         )
    )
  (time
    (->> (parallel-filter (heavy-func even?) (range 10) 5)
         (doall)
         )
    )
  )

(println (main))

