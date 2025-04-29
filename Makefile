build-uberjar:
	clj -M:uberjar
run:
	clj -M -m server.core
run-jar:
	java -cp target/blog.jar clojure.main -m server.core
