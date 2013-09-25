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

(defn authors
	"Splits the authors section string into individual author names"
	[authors]
	(clojure.string/split authors #"(, | and )"))
