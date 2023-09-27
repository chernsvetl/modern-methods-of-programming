(ns labs.lab1_1.lab1-1)

(defn generate-word-with-one-symbol [word, alphabet]
  (if (= 0 (count alphabet))
    ()
    (if (= (first alphabet) (first word))
      (generate-word-with-one-symbol word (rest alphabet))
      (->
        (cons (cons (first alphabet) word) (generate-word-with-one-symbol word (rest alphabet)))
        ;(println "-----------------------------------")
        )
      )
    )
  )

(defn generate-words [words, alphabet]
  (if (= 0 (count words))
    ()
    (concat (generate-word-with-one-symbol (first words) alphabet) (generate-words (rest words) alphabet))
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

(run! println (generate-permutations '("a" "b" "c" "a" "b")  3))