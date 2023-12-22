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
  ;"избавляемся от случаев, когда имеем дизъюнкцию и true -> true"
  (list

    [(fn [expr] (and (||? expr) (some const? (args expr))))
     (fn [expr] (if (some const-true? (args expr))
                  const-true
                    (apply ||
                           (map #(escape-constants-expr %) (and (#(= const-false %)) (args expr))))
                              ))]
    ;"избавляемся от случаев, когда имеем конъюнкцию и false -> false"
    [(fn [expr] (and (&&? expr) (some const? (args expr))))
     (fn [expr] (if (some const-false? (args expr))
                  const-false
                     (apply &&
                           (map #(escape-constants-expr %) (and (#(= const-true %)) (args expr))))
                              ))]

    [(fn [expr] (no? expr))
     (fn [expr] (no (escape-constants-expr  (second expr))))]

    [(fn [expr] (&&? expr))
     (fn [expr] (apply && (map #(escape-constants-expr %) (args expr))))]

    [(fn [expr] (||? expr))
     (fn [expr] (apply || (map #(escape-constants-expr %) (args expr))))]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)])
)

(defn escape-constants-expr [expr]
  (diff expr escape-constants-rules))