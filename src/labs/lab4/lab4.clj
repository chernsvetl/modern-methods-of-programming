(ns labs.lab4.lab4
  (:require [labs.lab4.api :refer :all])
  (:require [labs.lab4.escape_negations :refer :all])
  (:require [labs.lab4.base-expr :refer :all])
  (:require [labs.lab4.replacement :refer :all])
  (:require [labs.lab4.apply-for-predicates :refer :all])
  (:require [labs.lab4.escape_constants :refer :all])
  )

(defn simplification [var val expr]
  "функция для упрощения выражения"
  (escape-negations-expr
    (escape-constants-expr
      (define-expr var val
                   (base-operations-expr expr)))))

  (defn -main []
    (println (base-operations-expr (&& (variable :x) (variable :y))))
    (println (base-operations-expr (|| (variable :x) (variable :y))))
    (println (base-operations-expr (--> (variable :x) (variable :y))))
    (println (escape-negations-expr (no (no (variable :x)))))
    (println (escape-negations-expr (no (no const-false))))
    (println (escape-negations-expr (no const-true)))
    (println (base-operations-expr (&& (|| (variable :x) (variable :y)) (variable :z))))
    (println (base-operations-expr (xor (variable :x) (variable :y))))
    (println (simplification (variable :x) const-false (↓↓ (variable :x) const-false)))
    (println (simplification (variable :x) const-true (↓↓ (variable :x) const-false)))
    )

  (-main)
