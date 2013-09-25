(ns kindle-clippings.core)
  
(def txt (slurp "resources/My Clippings.txt"))

(def clipping-regex #"(.)*\r\n(.)*\r\n\r\n(.)*\r\n==========\r\n")

(defn clippings-seq
	"Separates each Kindle clipping in the input text into a sequence"
	[txt]
	(re-seq clipping-regex txt))
