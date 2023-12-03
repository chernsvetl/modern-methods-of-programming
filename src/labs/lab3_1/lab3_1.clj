(ns labs.lab3-1.lab3-1)


(defn heavy-func [f]
  (fn [coll]
    ;(Thread/sleep 100)
    (Thread/sleep 1)
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


(defn main []
  (def enter-coll [1 2 3 4 5 2.0 0.1 6.3])
  (time
    (->> (filter (heavy-func int?) enter-coll)
          (doall)
      )
    )
  (println (filter (heavy-func int?) enter-coll))
(println "-------------------------")
  (time
    (->> (parallel-filter (heavy-func int?) enter-coll 1)
         (doall)
         )
    )
  (time
    (->> (parallel-filter (heavy-func int?) enter-coll 3)
         (doall)
         )
    )
  (time
    (->> (parallel-filter (heavy-func int?) enter-coll 5)
         (doall)
         )
    )
  )

(println (main))

