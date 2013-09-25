(ns kindle-clippings.core)
  
(def txt (slurp "resources/My Clippings.txt"))

(def clipping-regex #"(.)*\r\n(.)*\r\n\r\n(.)*\r\n==========\r\n")

(defn clippings-seq
	"Separates each Kindle clipping from the input text into a sequence"
	[txt]
	(first (re-seq clipping-regex txt)))
	
(defn clipping-lines
	"Splits each clipping into its individual lines"
	[clipping]
	(clojure.string/split clipping #"\r\n")

(defn get-authors
	"Splits the authors section string into individual author names"
	[authors]
	(println authors)
	(clojure.string/split authors #", | and "))

(defn title-author-line
	[line1]
	(let [v (re-seq #"^([A-Za-z0-9 \(\)-,]+) \(([A-Za-z0-9 ,-]+)\)$" line1)]
		{:title (nth (first v) 1) :authors (get-authors (last (first v)))}))
