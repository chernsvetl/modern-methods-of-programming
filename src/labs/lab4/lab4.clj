(ns labs.lab4.lab4
  (:require [labs.lab4.operations :refer :all])
  (:require [labs.lab4.refuse-negations :refer :all])
  (:require [labs.lab4.base-formula :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  (:require [labs.lab4.apply-for-predicates :refer :all])
  )


  (defn -main []
    (println (base-operations-expr (conjunction (variable :x) (variable :y))))
    (println (base-operations-expr (disjunction (variable :x) (variable :y))))
    (println (base-operations-expr (implication (variable :x) (variable :y))))
    (println (refuse-expr-dubl-negation (negation (negation (variable :x)))))
    (println (refuse-expr-dubl-negation (negation (negation const-false))))
    (println (refuse-expr-dubl-negation (negation const-true)))
    )

  (-main)
