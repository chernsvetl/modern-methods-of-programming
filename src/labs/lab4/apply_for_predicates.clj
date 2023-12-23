(ns labs.lab4.apply-for-predicates
  (:require [labs.lab4.api :refer :all])
  (:require [labs.lab4.escape_negations :refer :all])
  (:require [labs.lab4.base-expr :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare apply-for-predicates-expr)

(def apply-for-predicates-expr-rules
  "список правил вывода"
  (list
    ; !(A && B)=!A || !B
    ; "правило для отрицания конъюнкции"
    [(fn [expr] (and (no? expr) (&&? (second expr))))
     (fn [expr] (apply-for-predicates-expr (apply || (map #(no %) (args (second expr))))))]

    ; !(A || B)=!A && !B
    ; "правило для отрицания дизъюнкции"
    [(fn [expr] (and (no? expr) (||? (second expr))))
     (fn [expr] (apply-for-predicates-expr (apply && (map #(no %) (args (second expr))))))]

    ; "правило для отрицания"
    [(fn [expr] (no? expr))
     (fn [expr] (no (apply-for-predicates-expr (second expr))))]

    ; "правило для вывода конъюнкции"
    [(fn [expr] (&&? expr))
     (fn [expr] (apply && (map #(apply-for-predicates-expr %) (args expr))))]

    ; "правило для вывода дизъюнкции"
    [(fn [expr] (||? expr))
     (fn [expr] (apply || (map #(apply-for-predicates-expr %) (args expr))))]

    ; "правило для вывода импликации"
    [(fn [expr] (-->? expr))
     (fn [expr] (apply --> (map #(apply-for-predicates-expr %) (args expr))))]

    ; "правило для проверки являться переменной или константой"
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]
    )
  )

(defn apply-for-predicates-expr [expr]
  (select-rule expr apply-for-predicates-expr-rules))