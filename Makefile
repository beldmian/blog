build-uberjar:
	clj -M:uberdeps
run:
	clj -M -m server.core
