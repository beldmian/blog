build-uberjar:
	clj -M -e "(compile 'server.core)"
	clojure -M:uberjar
run:
	clj -M -m server.core
