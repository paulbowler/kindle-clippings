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

(defn- split-authors
	"Splits the authors section string into individual author names"
	[authors]
	(clojure.string/split authors #", | and "))

(defn- get-authors
  "Get a set of authors from line 1"
	[line1]
	(split-authors (last (first (re-seq title-author-regex line1)))))

(defn- get-meta
  [line2]
  (flatten(re-seq meta-regex line2)))

(defn- clipping-lines
	"Splits each clipping into its individual lines"
	[clipping]
  (let [lines (filter #(not (empty? %)) (clojure.string/split (first clipping) #"[\r\n]"))
        line1 (first lines)
        line2 (second lines)
        clipping (nth lines 2)
        title (get-title line1)
        authors (get-authors line1)
        page (nth (get-meta line2) 1)
        loc (nth (get-meta line2) 2)]
          {:title title, :authors authors, :page page, :location loc, :clipping clipping}))

(defn parse [str]
  (map clipping-lines (clippings-seq str)))






