(ns labs.lab4.base-formula
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  )

(declare base-operations-expr)

(def base-operations-rules
  (list

    [(fn [expr] (and (conjunction? expr) (disjunction? (second expr))))
     (fn [expr] (base-operations-expr
                  (disjunction
                    (conjunction (first (args (first (args expr)))) (second (args expr)))
                    (conjunction (second (args (first (args expr)))) (second (args expr)))
                    )))
     ]

    [(fn [expr] (xor? expr))
     (fn [expr] (base-operations-expr
                  (disjunction
                    (conjunction (first (args expr)) (negation (second (args expr))))
                    (conjunction (negation(first (args expr))) (second (args expr))))))
     ]
    ; A* !B + !A*B
    ; (disjunction
    ; (conjunction (variable a) (negation (variable b)))
    ; (negation(variable a)) (variable b)

    [(fn [expr] (pier-arrow? expr))
     (fn [expr] (negation (disjunction (first (args expr)) (second (args expr)))))]


    [(fn [expr] (const-true? expr))
     (fn [expr] (const-true (base-operations-expr  (second expr))))]


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
               (fn [expr] (base-operations-expr (disjunction (negation (first (args expr))) (base-operations-expr (second (args expr))))))]

              )
    )

(defn base-operations-expr [expr]
  (diff expr base-operations-rules))
