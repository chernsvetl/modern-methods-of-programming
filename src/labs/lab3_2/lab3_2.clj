(ns labs.lab3-2.lab3-2)

(defn heavy-func [f]
  (fn [coll]
    (Thread/sleep 100)
    (f coll)
    )
  )

;Он должен вывести список из списков: ((1,...,100), (101,...,200),...)
(defn my-partition [coll]
  (map first (iterate
               (fn [[cur rem]]
                 [(take 5 rem) (drop 5 rem)])
               [(take 5 coll) (drop 5 coll)]
               )
       )
  )
;(println (take 5 (my-partition (iterate inc 1)))) - 5 это сколько списков взять

(defn lazy-parallel-filter [f coll]
  (let [heavy-f (heavy-func f)]
    (->>
      (take 3 (my-partition coll)) ; 3 это сколько списков взять
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
  (println(filter (heavy-func even?) (range 15)))
  (time
    (->> (filter (heavy-func even?)(take 15 (iterate inc 1)))
         (doall)
         )
    )
  )

(println (main))

