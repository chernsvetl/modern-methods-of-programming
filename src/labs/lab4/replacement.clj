(ns labs.lab4.replacement
  (:require [labs.lab4.api :refer :all])
  )

(defn select-rule [expr rules]
  "функция для выбора первого правила, условие которого соответствует заданному выражению"
  ((some (fn [rule] (if ((first rule) expr) (second rule) false))
         rules)
   expr)
  )

(declare define-expr)

(defn define-rules [var val]
  "функция для замены переменных на значения"
  (list

    ; "проверяет, является ли выражение переменной с тем же именем, что и var"
    [(fn [expr] (and (variable? expr) (same-variables? var expr)))
     (fn [expr] val)]

    ; "проверяет, является ли выражение переменной или константой"
    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    ; "проверяет, является ли выражение отрицанием"
    [(fn [expr] (no? expr))
     (fn [expr] (no (define-expr var val (second expr))))]

    ; "проверяет, является ли выражение конъюнкцией"
    [(fn [expr] (&&? expr))
     (fn [expr] (apply && (map #(define-expr var val %) (args expr))))]

    ; "проверяет, является ли выражение дизъюнкцией"
    [(fn [expr] (||? expr))
     (fn [expr] (apply || (map #(define-expr var val %) (args expr))))]

    )
  )

(defn define-expr [var val expr]
  (select-rule expr (define-rules var val)))
