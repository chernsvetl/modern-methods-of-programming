(ns labs.lab4.constant-refusion
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.refuse-negations :refer :all])
  (:require [labs.lab4.base-formula :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  (:require [labs.lab4.apply-for-predicates :refer :all])
  )

(declare constant-refuse-expr)
(def constant-refuse-rules
  "список правил вывода"
  (list

    [(fn [expr] (and (disjunction? expr) (some const? (args expr))))
     (fn [expr] (if (some const-true? (args expr))
                  const-true
                    (apply disjunction
                           (map #(constant-refuse-expr %) (and (#(= const-false %)) (args expr))))
                              ))]

    [(fn [expr] (and (conjunction? expr) (some const? (args expr))))
     (fn [expr] (if (some const-false? (args expr))
                  const-false
                     (apply conjunction
                           (map #(constant-refuse-expr %) (and (#(= const-true %)) (args expr))))
                              ))]

    [(fn [expr] (negation? expr))
     (fn [expr] (negation (constant-refuse-expr  (second expr))))]

    [(fn [expr] (conjunction? expr))
     (fn [expr] (apply conjunction (map #(constant-refuse-expr %) (args expr))))]

    [(fn [expr] (disjunction? expr))
     (fn [expr] (apply disjunction (map #(constant-refuse-expr %) (args expr))))]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)])
)

(defn constant-refuse-expr [expr]
  (diff expr constant-refuse-rules))
