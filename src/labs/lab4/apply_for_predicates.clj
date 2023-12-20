(ns labs.lab4.apply-for-predicates
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.refuse-negations :refer :all])
  (:require [labs.lab4.base-formula :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )


(declare apply-for-predicates-expr)

(def apply-for-predicates-expr-rules
  (list
    [(fn [expr] (and (negation? expr) (conjunction? (second expr))))
     (fn [expr] (apply-for-predicates-expr (apply disjunction (map #(negation %) (args (second expr))))))]

    [(fn [expr] (and (negation? expr) (disjunction? (second expr))))
     (fn [expr] (apply-for-predicates-expr (apply conjunction (map #(negation %) (args (second expr))))))]

    [(fn [expr] (negation? expr))
     (fn [expr] (negation (apply-for-predicates-expr  (second expr))))]


    [(fn [expr] (conjunction? expr))
     (fn [expr] (apply conjunction (map #(apply-for-predicates-expr %) (args expr))))]


    [(fn [expr] (disjunction? expr))
     (fn [expr] (apply disjunction (map #(apply-for-predicates-expr %) (args expr))))]

    [(fn [expr] (implication? expr))
     (fn [expr] (apply implication (map #(apply-for-predicates-expr %) (args expr))))]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]
    )
  )

(defn apply-for-predicates-expr [expr]
  (diff expr apply-for-predicates-expr-rules))