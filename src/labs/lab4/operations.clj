(ns labs.lab4.operations)


(defn variable [name]
  {:pre [(keyword? name)]}
  (list :var name))

(def const-true (list :true))

(def const-false (list :false))

(defn const-true? [expr]
  (= (first expr) :true))

(defn const-false? [expr]
  (= (first expr) :false))

(defn const? [expr]
  (or (const-false? expr)
      (const-true? expr)))

(defn variable? [expr]
  (= (first expr) :var))

(defn variable-name [v]
  (second v))

(defn same-variables? [v1 v2]
  (and
    (variable? v1)
    (variable? v2)
    (= (variable-name v1) (variable-name v2))))

(defn args [expr]
  (rest expr))

(defn negation [expr & rest]
  (cons :negation (cons expr rest)))

(defn negation? [expr]
  (= :negation (first expr)))

(defn conjunction [expr & rest]
  (cons :conjunction (cons expr rest)))

(defn conjunction? [expr]
  (= :conjunction(first expr)))

(defn disjunction [expr & rest]
  (cons :disjunction (cons expr rest)))

(defn disjunction? [expr]
  (= :disjunction (first expr)))

(defn implication [expr & rest]
  (cons :implication (cons expr rest)))

(defn implication? [expr]
  (= :implication(first expr)))

(defn xor [expr & rest]
  (cons :xor (cons expr rest)))

(defn xor? [expr]
  (= :xor(first expr)))

(defn pier-arrow [expr & rest]
  (cons :pier-arrow (cons expr rest)))

(defn pier-arrow? [expr]
  (= :pier-arrow(first expr)))
