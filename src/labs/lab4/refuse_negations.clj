(ns labs.lab4.refuse-negations
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare refuse-expr-dubl-negation)

(def refuse-expr-dubl-negation-rules
  (list

    [(fn [expr] (and (negation? expr) (const-true? (first (args expr)))))
     (fn [expr] const-false)]

    [(fn [expr] (and (negation? expr) (const-false? (first (args expr)))))
     (fn [expr] const-true)]

    [(fn [expr] (and (negation? expr) (negation? (second expr))))
     (fn [expr] (refuse-expr-dubl-negation (first (args (second expr)))))]

    [(fn [expr] (negation? expr))
     (fn [expr] (negation (refuse-expr-dubl-negation  (second expr))))]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    [(fn [expr] (and (negation? expr) (negation? (second expr))))
     (fn [expr] (refuse-expr-dubl-negation (first (args (second expr)))))]

    )
  )

(defn refuse-expr-dubl-negation [expr]
  (diff expr refuse-expr-dubl-negation-rules))
