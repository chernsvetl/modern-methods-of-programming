(ns labs.lab4.base-expr
  (:require [labs.lab4.api :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare base-operations-expr)

(def base-operations-rules
  "список правил вывода"
  (list

    ; "правило для конъюнкции дизъюнкции"
    [(fn [expr] (and (&&? expr) (||? (second expr))))
     (fn [expr] (base-operations-expr
                  (||
                    (&& (first (args (first (args expr)))) (second (args expr)))
                    (&& (second (args (first (args expr)))) (second (args expr)))
                    )))]

    ; "правило для операции исключающее или"
    [(fn [expr] (xor? expr))
     (fn [expr] (base-operations-expr
                  (||
                    (&& (first (args expr)) (no (second (args expr))))
                    (&& (no(first (args expr))) (second (args expr))))))]

    ; "правило для стрелки Пирса"
    [(fn [expr] (↓↓? expr))
     (fn [expr] (no (|| (first (args expr)) (second (args expr)))))]

    ; "правило для проверки значения константы в true"
    [(fn [expr] (const-true? expr))
     (fn [expr] (const-true (base-operations-expr  (second expr))))]

    ; "правило для проверки значения константы в false"
    [(fn [expr] (const-false? expr))
     (fn [expr] (const-false (base-operations-expr  (second expr))))]

    ; "правило для проверки являться переменной или константой"
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    ; "правило для конъюнкции"
    [(fn [expr] (&&? expr))
     (fn [expr] (apply && (map #(base-operations-expr %) (args expr))))]

    ; "правило для дизъюнкции"
    [(fn [expr] (||? expr))
     (fn [expr] (apply || (map #(base-operations-expr %) (args expr))))]

    ; "правило для отрицания"
    [(fn [expr] (no? expr))
     (fn [expr] (apply no (first (args expr))))]

    ; "правило для отрицания false"
    [(fn [expr] (and (no? expr) (const-false? (second expr))))
     (fn [expr] (apply no (first (args expr))))]

    ; "правило для отрицания true"
    [(fn [expr] (and (no? expr) (const-true? (second expr))))
     (fn [expr] (apply no (first (args expr))))]

    ; "правило для импликации"
    [(fn [expr] (-->? expr))
     (fn [expr] (base-operations-expr (|| (no (first (args expr))) (base-operations-expr (second (args expr))))))]

      )
    )

(defn base-operations-expr [expr]
  (select-rule expr base-operations-rules))
