(ns labs.lab4.replacement
  (:require [labs.lab4.operations :refer :all])
  )

(defn diff [expr rules]
  ((some (fn [rule] (if ((first rule) expr) (second rule) false))
         rules)
   expr)
  )

(declare define-expr)

(defn define-rules [var val]
  "функция для замены переменной на значение"
  (list

    [(fn [expr] (and (variable? expr) (same-variables? var expr)))
     (fn [expr] val)]

    [(fn [expr] (or (variable? expr) (const? expr)))
     (fn [expr] expr)]

    [(fn [expr] (negation? expr))
     (fn [expr] (negation (define-expr var val (second expr))))]

    [(fn [expr] (conjunction? expr))
     (fn [expr] (apply conjunction (map #(define-expr var val %) (args expr))))]

    [(fn [expr] (disjunction? expr))
     (fn [expr] (apply disjunction (map #(define-expr var val %) (args expr))))]

    )
  )

(defn define-expr [var val expr]
  (diff expr (define-rules var val)))
