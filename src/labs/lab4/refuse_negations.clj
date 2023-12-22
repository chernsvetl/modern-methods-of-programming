(ns labs.lab4.refuse-negations
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare refuse-expr-negation)

(def refuse-expr-negation-rules
  "список правил вывода"
  (list

    "правило замены отрицания true -> false"
    [(fn [expr] (and (negation? expr) (const-true? (first (args expr)))))
     (fn [expr] const-false)]

    "правило замены отрицания false -> true"
    [(fn [expr] (and (negation? expr) (const-false? (first (args expr)))))
     (fn [expr] const-true)]

    [(fn [expr] (and (negation? expr) (negation? (second expr))))
     (fn [expr] (refuse-expr-negation (first (args (second expr)))))]

    [(fn [expr] (negation? expr))
     (fn [expr] (negation (refuse-expr-negation  (second expr))))]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    [(fn [expr] (and (negation? expr) (negation? (second expr))))
     (fn [expr] (refuse-expr-negation (first (args (second expr)))))]

    )
  )

(defn refuse-expr-negation [expr]
  (diff expr refuse-expr-negation-rules))
