(ns labs.lab4.escape_constants
  (:require [labs.lab4.api :refer :all])
  (:require [labs.lab4.escape_negations :refer :all])
  (:require [labs.lab4.base-expr :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  (:require [labs.lab4.apply-for-predicates :refer :all])
  )

(declare escape-constants-expr)

"список правил вывода"
(def escape-constants-rules
  (list
    ; "правило для избавления от случаев, когда имеем дизъюнкцию и true -> true"
    [(fn [expr] (and (||? expr) (some const? (args expr))))
     (fn [expr]
       (if (some const-true? (args expr))
         const-true
         (apply ||
                (map #(escape-constants-expr %) (filter #(not= const-false %) (args expr))))))]

    ; "правило для избавления от случаев, когда имеем конъюнкцию и false -> false"
    [(fn [expr] (and (&&? expr) (some const? (args expr))))
     (fn [expr]
       (if (some const-false? (args expr))
         const-false
         (apply &&
                (map #(escape-constants-expr %) (filter #(not= const-true %) (args expr))))))]

    ; "правило для проверки является ли выражение отрицанием"
    [(fn [expr] (no? expr))
     (fn [expr] (no (escape-constants-expr  (second expr))))]

    ; "правило для проверки является ли выражение конъюнкцией"
    [(fn [expr] (&&? expr))
     (fn [expr] (apply && (map #(escape-constants-expr %) (args expr))))]

    ; "правило для проверки является ли выражение дизъюнкцией"
    [(fn [expr] (||? expr))
     (fn [expr] (apply || (map #(escape-constants-expr %) (args expr))))]

    ; "правило для проверки является ли выражение переменной или константой"
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    )
)

(defn escape-constants-expr [expr]
  (select-rule expr escape-constants-rules))
