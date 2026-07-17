(ns culture.facts-test
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [culture.facts :as facts]))

(deftest amsterdam-has-culture-basis
  (let [sb (facts/spec-basis "amsterdam")]
    (is (= 9 (count sb)))
    (is (= (count sb) (count (set (map :culture/id sb)))))
    (is (every? #(str/starts-with? (:culture/url %) "https://") sb))
    (is (every? #(= "amsterdam" (:culture/municipality %)) sb))
    (is (every? #(= "NLD" (:culture/country %)) sb))
    (is (every? #(seq (:culture/summary %)) sb))
    (is (every? #(string? (:culture/retrieved-at %)) sb))))

(deftest unknown-municipality-has-no-basis
  (is (nil? (facts/spec-basis "rotterdam")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["amsterdam" "rotterdam"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["rotterdam"] (:missing-municipalities c)))))

(deftest by-kind-filters
  (is (= 3 (count (facts/by-kind "amsterdam" :dish))))
  (is (= ["amsterdam.craft.coster-diamonds"]
         (mapv :culture/id (facts/by-kind "amsterdam" :craft))))
  (is (= 2 (count (facts/by-kind "amsterdam" :beverage))))
  (is (empty? (facts/by-kind "rotterdam" :dish))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/culture-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))
