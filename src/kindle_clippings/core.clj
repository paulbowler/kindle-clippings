(ns kindle-clippings.core)

(def txt (slurp "resources/My Clippings.txt"))
(def clipping-regex #"(.)*\r\n- Highlight (.)*\r\n\r\n(.)*[?=\r\n==========\r\n]")
(def title-author-regex #"^([^\r\n]+) \(([^\r\n\(\)]+)\)$")
(def meta-regex #"^- Highlight(?: on Page )?([0-9a-z]*)?(?: \|)? Loc. (.*)  \| Added on (.*)$")

(defn- clippings-seq
  "Separates each Kindle clipping from the input text into a sequence"
  [txt]
  (re-seq clipping-regex txt))

(defn- get-title
  "Get the title from the first line"
  [line1]
  (second (first (re-seq title-author-regex line1))))

(defn- format-author
  "Reformat the author names with correct capitalization"
  [author]
  (clojure.string/join " " (map clojure.string/capitalize (clojure.string/split author #"[ ]"))))

(defn- split-authors
  "Splits the authors section string into individual author names"
  [authors]
  (map format-author (clojure.string/split authors #", | and ")))

(defn- get-authors
  "Get a set of authors from line 1"
  [line1]
  (split-authors (last (first (re-seq title-author-regex line1)))))

(defn- get-meta
   "Get the page number (if it exists) and clipping locaiton"
  [line2]
  (flatten(re-seq meta-regex line2)))

(defn- clipping-lines
  "Splits each clipping into a map of attributes"
  [clipping]
  (let [lines (filter #(not (empty? %)) (clojure.string/split (first clipping) #"[\r\n]"))
        line1 (first lines)
        line2 (second lines)
        clipping (nth lines 2)
        title (get-title line1)
        authors (into '() (get-authors line1))
        page (nth (get-meta line2) 1)
        loc (nth (get-meta line2) 2)]
    {:title title, :authors authors, :page page, :location loc, :clipping clipping}))

(defn parse [str]
  "Parses a Kindle clippings string into a map"
  (map clipping-lines (clippings-seq str)))






