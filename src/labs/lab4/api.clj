(ns labs.lab4.api)

(defn variable [name]
  "порождение переменной"
  {:pre [(keyword? name)]}
  (list :var name))

(def const-true
  ;"порождение константы true"
  (list :true))

(def const-false
  ;"порождение константы false"
  (list :false))

(defn const-true? [expr]
  ;"проверка того, что константа true"
  (= (first expr) :true))

(defn const-false? [expr]
  ;"проверка того, что константа false"
  (= (first expr) :false))

(defn const? [expr]
  ;"проверка типа для константы"
  (or (const-false? expr)
      (const-true? expr)))

(defn variable? [expr]
  ;"проверка типа для переменной"
  (= (first expr) :var))

(defn variable-name [v]
  ;"получение значения для переменной"
  (second v))

(defn same-variables?
  ;"сравнение переменных"
  [v1 v2]

  (and
    (variable? v1)
    (variable? v2)
    (= (variable-name v1) (variable-name v2))))

(defn args [expr]
  ;"список аргументов выражения"
  (rest expr))

(defn no [expr & rest]
  ;"порождение отрицания"
  (cons :no (cons expr rest)))

(defn no? [expr]
  ;"проверка типа для отрицания"
  (= :no (first expr)))

(defn && [expr & rest]
  ;"порождение конъюнкции"
  (cons :&& (cons expr rest)))

(defn &&? [expr]
  ;"проверка типа для конъюнкции"
  (= :&&(first expr)))

(defn || [expr & rest]
  ;"порождение дизъюнкции"
  (cons :|| (cons expr rest)))

(defn ||? [expr]
  ;"проверка типа для дизъюнкции"
  (= :|| (first expr)))

(defn --> [expr & rest]
  ;"порождение импликации"
  (cons :--> (cons expr rest)))

(defn -->? [expr]
  ;"проверка типа для импликации"
  (= :-->(first expr)))

(defn xor [expr & rest]
  ; "порождение XOR"
  (cons :xor (cons expr rest)))

(defn xor? [expr]
  ;"проверка типа для XOR"
  (= :xor(first expr)))

(defn ↓↓ [expr & rest]
  "порождение стрелки Пирса"
  (cons :↓↓ (cons expr rest)))

(defn ↓↓? [expr]
  "проверка типа для стрелки Пирса"
  (= :↓↓(first expr)))
