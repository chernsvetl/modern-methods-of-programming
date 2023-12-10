(ns labs.lab3-2.lab3-2)

(defn main-func [n]
  (= 0 (mod n 2))
  )

(defn heavy-func [f]
  (fn [coll]
    (Thread/sleep 1)
    (f coll)
    )
  )

(defn my-partition
  [block-size lazy-coll]
  (take-while
    (partial seq)
    (->>
           (iterate
           (fn [[block tail]]
             [(take block-size tail) (drop block-size tail)])
           [(take block-size lazy-coll) (drop block-size lazy-coll)])
         (map #(first %)))))


(defn lazy-parallel-filter
  [pred coll block-size num-threads]
  (->>
       (my-partition block-size coll) ;1
       (map #(future (doall (filter pred %)))) ;2 каждой подпоследовательности сопоставится future
       ; благодаря future внутренние потоки выполняются параллельно
       (my-partition num-threads)      ;3
       (map #(future (mapcat deref (doall %))))  ;4 запуск первого внешнего потока, после его заврешения запускатся второй внешний поток и так далее
       ; внешний поток запускает внутренние потоки внутри подпоследовательности, которые будут выполняться параллельно и после делает их конкатенацию списков
       (mapcat deref)))  ;5 соединение результатов внешних потоков


(defn main []
  (time (doall (filter (heavy-func main-func) (range 100))))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 1)))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 2)))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 4)))
  (time (doall (lazy-parallel-filter (heavy-func main-func) (range 100) 10 8)))

  (time (doall (take 1000 (filter (heavy-func main-func) (iterate inc 1)))))
  (time (doall (take 1000 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 1))))
  (time (doall (take 1000 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 2))))
  (time (doall (take 1000 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 4))))
  (time (doall (take 1000 (lazy-parallel-filter (heavy-func main-func) (iterate inc 1) 10 8))))
  )

(println (main))




