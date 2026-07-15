(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Amsterdam -- the SEVENTH
  municipality-level entry (see cloud-itonami-municipality-jpn-tokyo,
  -usa-washington-dc, -gbr-london, -can-toronto, -deu-berlin, -fra-paris
  for the first six) per ADR-2607141700 (cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL lokaleregelgeving.overheid.nl (the Dutch
  national portal for local/municipal legislation, indexed by CVDR
  identifier) URL -- never fabricated. An ordinance not in this table
  has NO spec-basis, full stop; extend `catalog`, do not invent an
  id/url/number.

  Both entries below were directly WebFetch-verified against the live
  lokaleregelgeving.overheid.nl page on 2026-07-15. Each citation is to
  a SPECIFIC, dated version of the regulation (the portal exposes
  version history per CVDR id) rather than an undated 'current text'
  claim -- the Algemene Plaatselijke Verordening 2008 citation is to its
  original version 1 (valid from 2008-11-01), and the Huisvestingsverordening
  citation is to its 2020 version (valid from 2020-01-01); the current
  in-force text may differ after later amendments not tracked here.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"amsterdam"
   [{:ordinance/id "amsterdam.algemene-plaatselijke-verordening-2008"
     :ordinance/title "Algemene Plaatselijke Verordening 2008 (General Local Bylaw)"
     :ordinance/municipality "amsterdam"
     :ordinance/country "NLD"
     :ordinance/kind :ordinance
     :ordinance/number "CVDR72510"
     :ordinance/url "https://lokaleregelgeving.overheid.nl/CVDR72510/1"
     :ordinance/url-provenance :official-overheid-nl-lokaleregelgeving
     :ordinance/enacted-date "2008-11-01"
     :ordinance/retrieved-at "2026-07-15"
     :ordinance/topic #{:public-order :licensing}}
    {:ordinance/id "amsterdam.huisvestingsverordening-2020"
     :ordinance/title "Huisvestingsverordening Amsterdam 2020 (Housing Ordinance)"
     :ordinance/municipality "amsterdam"
     :ordinance/country "NLD"
     :ordinance/kind :ordinance
     :ordinance/number "CVDR635633"
     :ordinance/url "https://lokaleregelgeving.overheid.nl/CVDR635633/1"
     :ordinance/url-provenance :official-overheid-nl-lokaleregelgeving
     :ordinance/enacted-date "2020-01-01"
     :ordinance/retrieved-at "2026-07-15"
     :ordinance/topic #{:housing :short-term-rental}}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-nld-amsterdam Wave 0 (ADR-2607141700): "
                 (count (get catalog "amsterdam")) " Amsterdam entries seeded with "
                 "an official lokaleregelgeving.overheid.nl (CVDR) citation. "
                 "Extend `ordinance.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
