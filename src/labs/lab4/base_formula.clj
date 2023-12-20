(ns labs.lab4.base-formula
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

; список правил
(declare base-operations-expr)

(def base-operations-rules
  (list
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    [(fn [expr] (conjunction? expr))
     (fn [expr] (apply conjunction (map #(base-operations-expr %) (args expr))))]

    [(fn [expr] (disjunction? expr))
     (fn [expr] (apply disjunction (map #(base-operations-expr %) (args expr))))]

    [(fn [expr] (negation? expr))
     (fn [expr] (apply negation (first (args expr))))]

    [(fn [expr] (and (negation? expr) (const-false? (second expr))))
     (fn [expr] (apply negation (first (args expr))))]

    [(fn [expr] (implication? expr))
     (fn [expr] (base-operations-expr (disjunction (negation (first (args expr))) (base-operations-expr(second (args expr))))))]

    )
  )

(defn base-operations-expr [expr]
  (diff expr base-operations-rules))