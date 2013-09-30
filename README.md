# kindle-clippings

A Clojure library to parse a string representation of the 'My_Clippings.txt' file on Kindle book readers.

## Usage

```clojure
(parse (slurp "resources/My Clippings.txt"))
```

Returns a sequence of maps, thus:

```clojure
({:title "How an Economy Grows and Why It Crashes", :authors ("Andrew J. Schiff" "Peter D. Schiff"),
:page "5", :location "1134-37",
:clipping "During the Depression, President Roosevelt decided to devalue the dollar..."})
```

## License

Copyright © 2013 Paul Bowler

Distributed under the Eclipse Public License version 10.0
