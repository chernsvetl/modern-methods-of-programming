(ns labs.lab4.escape_negations
  (:require [labs.lab4.api :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare escape-negations-expr)

(def escape-negations-rules
  "список правил вывода"
  (list

    ; "правило замены отрицания true -> false"
    [(fn [expr] (and (no? expr) (const-true? (first (args expr)))))
     (fn [expr] const-false)]

    ; "правило замены отрицания false -> true"
    [(fn [expr] (and (no? expr) (const-false? (first (args expr)))))
     (fn [expr] const-true)]

    ; "правило для двойного отрицания переменной"
    [(fn [expr] (and (no? expr) (no? (second expr))))
     (fn [expr] (escape-negations-expr (first (args (second expr)))))]

    ; "правило для проверки является ли выражение отрицанием"
    [(fn [expr] (no? expr))
     (fn [expr] (no (escape-negations-expr  (second expr))))]

    ; "правило для проверки являться переменной или константой"
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    )
  )

(defn escape-negations-expr [expr]
  (select-rule expr escape-negations-rules))
