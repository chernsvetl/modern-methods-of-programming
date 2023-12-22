(ns labs.lab4.operations)

(defn variable [name]
  "порождение переменной"
  {:pre [(keyword? name)]}
  (list :var name))

(def const-true "порождение константы true"
  (list :true))

(def const-false ";порождение константы false" (list :false))

(defn const-true? [expr]
  "проверка того, что константа true"
  (= (first expr) :true))

(defn const-false? [expr]
  "проверка того, что константа false"
  (= (first expr) :false))

(defn const? [expr]
  "проверка типа для константы"
  (or (const-false? expr)
      (const-true? expr)))

(defn variable? [expr]
  "проверка типа для переменной"
  (= (first expr) :var))

(defn variable-name [v]
  "получение значения для переменной"
  (second v))

(defn same-variables? "сравнение переменных" [v1 v2]

  (and
    (variable? v1)
    (variable? v2)
    (= (variable-name v1) (variable-name v2))))

(defn args [expr]
  "список аргументов выражения"
  (rest expr))

(defn negation [expr & rest]
  "порождение отрицания"
  (cons :negation (cons expr rest)))

(defn negation? [expr]
  "проверка типа для отрицания"
  (= :negation (first expr)))

(defn conjunction [expr & rest]
  "порождение конъюнкции"
  (cons :conjunction (cons expr rest)))

(defn conjunction? [expr]
  "проверка типа для конъюнкции"
  (= :conjunction(first expr)))

(defn disjunction [expr & rest]
  "порождение дизъюнкции"
  (cons :disjunction (cons expr rest)))

(defn disjunction? [expr]
  "проверка типа для дизъюнкции"
  (= :disjunction (first expr)))

(defn implication [expr & rest]
  "порождение импликации"
  (cons :implication (cons expr rest)))

(defn implication? [expr]
  "проверка типа для импликации"
  (= :implication(first expr)))

(defn xor [expr & rest]
  "порождение XOR"
  (cons :xor (cons expr rest)))

(defn xor? [expr]
  "проверка типа для XOR"
  (= :xor(first expr)))

(defn pier-arrow [expr & rest]
  "порождение стрелки Пирса"
  (cons :pier-arrow (cons expr rest)))

(defn pier-arrow? [expr]
  "проверка типа для стрелки Пирса"
  (= :pier-arrow(first expr)))
