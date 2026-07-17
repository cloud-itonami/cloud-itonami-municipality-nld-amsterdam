(ns culture.facts
  "Regional-culture catalog for Amsterdam (Gemeente Amsterdam) -- local
  dishes, protected products, beverages, festivals and heritage sites,
  piggybacked onto this municipality compliance repo per ADR-2607171400
  (cloud-itonami-municipality-culture-catalog, in com-junkawasaki/root),
  sibling namespace to `ordinance.facts` (ADR-2607141700).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "municipality-slug -> vector of culture entries."
  {"amsterdam"
   [{:culture/id "amsterdam.dish.ossenworst"
     :culture/name "Ossenworst"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :dish
     :culture/summary "Raw beef sausage originating in Amsterdam, originally made of ox meat, with roots tracing back to the seventeenth century."
     :culture/url "https://en.wikipedia.org/wiki/Ossenworst"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.dish.bitterballen"
     :culture/name "Bitterballen"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :dish
     :culture/summary "Dutch meat-based snack, popularly served as part of a bittergarnituur (a selection of savoury snacks) at Dutch pubs and receptions."
     :culture/url "https://en.wikipedia.org/wiki/Bitterballen"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.dish.stroopwafel"
     :culture/name "Stroopwafel"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :dish
     :culture/summary "Dutch waffle treat that originated in Gouda in the late 18th or early 19th century, popular throughout the Netherlands."
     :culture/url "https://en.wikipedia.org/wiki/Stroopwafel"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.beverage.heineken"
     :culture/name "Heineken"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :beverage
     :culture/summary "Dutch pale lager; the company was founded in 1864 when Gerard Adriaan Heineken bought the De Hooiberg brewery on the Nieuwezijds Achterburgwal canal in Amsterdam."
     :culture/url "https://en.wikipedia.org/wiki/Heineken"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.beverage.jenever"
     :culture/name "Jenever"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :beverage
     :culture/summary "Juniper-flavoured traditional liquor of the Netherlands, Belgium and adjoining areas of northern France and northwestern Germany."
     :culture/url "https://en.wikipedia.org/wiki/Jenever"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.craft.coster-diamonds"
     :culture/name "Diamond polishing (Royal Coster Diamonds)"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :craft
     :culture/summary "Royal Coster Diamonds, a diamond polishing factory in Amsterdam founded in 1840, is the oldest in the world."
     :culture/url "https://en.wikipedia.org/wiki/Coster_Diamonds"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.festival.pride-amsterdam"
     :culture/name "Pride Amsterdam"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :festival
     :culture/summary "Annual LGBT festival held in Amsterdam during the first weekend of August, whose peak event is the Canal Parade of boats on the first Saturday."
     :culture/url "https://en.wikipedia.org/wiki/Pride_Amsterdam"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.festival.koningsdag"
     :culture/name "King's Day"
     :culture/name-local "Koningsdag"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :festival
     :culture/summary "Dutch national holiday celebrated on 27 April; an outdoor concert on Amsterdam's Museumplein may gather as many as 800,000 people."
     :culture/url "https://en.wikipedia.org/wiki/Koningsdag"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "amsterdam.heritage.canals-of-amsterdam"
     :culture/name "Canals of Amsterdam"
     :culture/municipality "amsterdam"
     :culture/country "NLD"
     :culture/kind :heritage
     :culture/summary "The 17th-century canal ring area, including the Prinsengracht, Keizersgracht, Herengracht and Jordaan, was listed as a UNESCO World Heritage Site in 2010."
     :culture/url "https://en.wikipedia.org/wiki/Canals_of_Amsterdam"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-municipality-nld-amsterdam culture catalog "
                 "(ADR-2607171400): " (count (get catalog "amsterdam"))
                 " Amsterdam entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [muni kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis muni)))
