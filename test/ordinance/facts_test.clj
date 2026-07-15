(ns ordinance.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [ordinance.facts :as facts]))

(deftest amsterdam-has-spec-basis
  (let [sb (facts/spec-basis "amsterdam")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:ordinance/url %) "https://lokaleregelgeving.overheid.nl/") sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "rotterdam")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["amsterdam" "rotterdam"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["rotterdam"] (:missing-municipalities c)))))

(deftest by-topic-filters
  (is (= ["amsterdam.huisvestingsverordening-2020"]
         (mapv :ordinance/id (facts/by-topic "amsterdam" :short-term-rental))))
  (is (empty? (facts/by-topic "amsterdam" :labor)))
  (is (empty? (facts/by-topic "rotterdam" :housing))))
