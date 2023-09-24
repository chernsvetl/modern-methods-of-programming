(ns labs.lab1-1)

(defn generate-word-with-one-symbol [word, alphabet] ;строим комбинации из одного символа, то есть тут список из комбинвций a,b,c

  (if (= 0 (count alphabet))
    ()
      (if (identical? (first alphabet) (first word))
        (generate-word-with-one-symbol word (rest alphabet))
        (->
          (cons (cons (first alphabet) word) (generate-word-with-one-symbol word (rest alphabet)))
          ;(println "----------------------------------------------------------")
          ;(println word)
          )
        )
      )
  )
 

(defn generate-words [words, alphabet]    ; идем по каждой комбинации (сначала a потом b потом c, после из комбинаций дальше ba, ba, ab и т д)
  (if (= 0 (count words))
    ()
  (concat (generate-word-with-one-symbol (first words) alphabet) (generate-words (rest words) alphabet))
    )
  )

(defn looping [words alphabet n]
  (if (= n 0)
    words
  (looping (generate-words words alphabet) alphabet (dec n))
    )
  )


(defn remove-duplicates-letters-in-alphabet [alphabet]  ;удаление дублирующихся символов в алфавите
  (set alphabet)
  )

(defn generate-permutations [alphabet n]
  (if (= n 0)
    ()
    (looping '(())  (remove-duplicates-letters-in-alphabet alphabet)  n)
    )
  )


(run! println (generate-permutations '("a" "b" "c", "a", "b")  3)) ;run для переноса на каждую строку

;(println (remove-duplicates '("a" "b" "c", "a")))
;(generate-word-with-one-symbol "a" '["a" "b" "c"])

