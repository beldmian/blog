(ns blog.blog-test
  (:require [blog.metadata :refer [parse-frontmatter]]
            [clojure.test :refer [deftest is]]))

(deftest parse-frontmatter-test
  (is (= {:metadata {:hello "world"}, :content "content"}
         (parse-frontmatter "---\nhello:world\n---content")))
  (is (= {:metadata {}, :content "content"} (parse-frontmatter "content"))))
