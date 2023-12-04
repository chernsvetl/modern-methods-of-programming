(ns labs.lab3-2.lab3-2)

(defn heavy-func [f]
  (fn [coll]
    (Thread/sleep 300)
    (f coll)
    )
  )

(defn my-partition
  [lazy-coll batch-size]
  (take-while
    (partial seq)
    (->> (iterate
           (fn [[batch tail]]
             [(take batch-size tail) (drop batch-size tail)])
           [(take batch-size lazy-coll) (drop batch-size lazy-coll)])
         (map #(first %)))))

(defn lazy-parallel-filter [f coll]
  (let [heavy-f (heavy-func f)]
    (->>
      (take 50 (my-partition coll 50))
      (map #(future (doall(filter heavy-f %))))
      (doall)
      (mapcat deref)
      )
    )
  )

(defn main []
  (time
    (->> (lazy-parallel-filter (heavy-func even?)(iterate inc 1))
         (doall)
         )
    )
  (println(filter (heavy-func even?) (range 150)))
  (time
    (->> (filter (heavy-func even?)(take 150 (iterate inc 1)))
         (doall)
         )
    )
  )

(println (main))


