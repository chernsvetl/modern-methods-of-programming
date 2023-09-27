(ns labs.lab1-2.lab1-2)

(defn generate-word-with-one-symbol [word alphabet]
  (def list_ '())
  ((defn generate-word-with-one-symbol-inner [alphabet-remain acc]
     (if (= 0 (count alphabet-remain))
       acc
       (if (not= (first word) (first alphabet-remain))
         (recur (rest alphabet-remain) (cons (cons (first alphabet-remain) word) acc))
         (recur (rest alphabet-remain) acc)
         )
       )
     ) alphabet list_)
  )


(defn generate-words [words alphabet]
  (def list_ '())
  ((defn generate-words-inner [words-remain acc]
     (if (= 0 (count words-remain))
       acc
       (recur (rest words-remain) (concat acc (generate-word-with-one-symbol (first words-remain) alphabet)))
       )
     )
   words list_)
  )


(defn generate-permutations [alphabet n]
  (def list_ '(()))
  ((defn generate-permutations-inner [n acc]
     (if (< n 0)
       (println "invalid n value")
       (if (= 0 n)
         acc
         (recur (dec n) (generate-words acc (set alphabet)))
         )
       )
     )
   n list_)
  )

(run! println (reverse(generate-permutations '("a" "b" "c" "a" "b")  3)))