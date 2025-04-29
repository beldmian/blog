build-uberjar:
	clojure -M:uberjar
run:
	clj -M -m server.core
