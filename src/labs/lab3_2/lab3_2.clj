(ns labs.lab3-2.lab3-2)

(defn is-even
  [n]
  (= 0 (mod n 2)))

(defn heavy-func
  [n]
  (Thread/sleep 1)
  (is-even n))


(defn my-partition
  [batch-size lazy-coll]
  (take-while
    (partial seq)
    (->> (iterate
           (fn [[batch tail]]
             [(take batch-size tail) (drop batch-size tail)])
           [(take batch-size lazy-coll) (drop batch-size lazy-coll)])
         (map #(first %)))))


(defn lazy-parallel-filter
  [pred coll batch-size num-threads]
  (->> (my-partition batch-size coll) ;1
       (map #(future (doall (filter pred %)))) ;2
       (my-partition num-threads)      ;3
       (map #(future (mapcat deref (doall %))))  ;4
       (mapcat deref)))  ;5


(defn main []
  (time (doall (filter heavy-func (range 100))))
  (time (doall (lazy-parallel-filter heavy-func (range 100) 10 1)))
  (time (doall (lazy-parallel-filter heavy-func (range 100) 10 2)))
  (time (doall (lazy-parallel-filter heavy-func (range 100) 10 4)))
  (time (doall (lazy-parallel-filter heavy-func (range 100) 10 8)))

  (time (doall (take 1000 (filter heavy-func (iterate inc 1)))))
  (time (doall (take 1000 (lazy-parallel-filter heavy-func (iterate inc 1) 10 1))))
  (time (doall (take 1000 (lazy-parallel-filter heavy-func (iterate inc 1) 10 2))))
  (time (doall (take 1000 (lazy-parallel-filter heavy-func (iterate inc 1) 10 4))))
  (time (doall (take 1000 (lazy-parallel-filter heavy-func (iterate inc 1) 10 8))))

  )

(println (main))




