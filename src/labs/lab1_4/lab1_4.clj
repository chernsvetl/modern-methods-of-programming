(ns labs.lab1_4.lab1-4)

(defn generate-word-with-one-symbol [word, alphabet]
  (if (= 0 (count alphabet))
    ()
  (map #(cons % word) (filter #(not= % (first word))  alphabet))
    )
  )


(defn generate-words [words, alphabet]
  (if (= 0 (count words))
    ()
  (reduce #(concat %1 %2)
          (map #(generate-word-with-one-symbol % alphabet) words)
          )
    )
  )


(defn generate-permutations [alphabet n]
  (def list_ '(()))
  (if (> n 0)
    (generate-words (generate-permutations alphabet (dec n)) (set alphabet))
    (if (< n 0)
      (println "invalid n value")
      list_
      )
    )
  )

(run! println (generate-permutations '("a" "b" "c" "a" "b") 3))
