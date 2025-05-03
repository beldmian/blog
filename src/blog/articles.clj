(ns blog.articles
  (:require [blog.metadata :as metadata]
            [clojure.string :as str]))

(defrecord Article [title description date contents tags])

;; Function to load all articles at compile time
(defn load-articles-at-compile-time
  "Load all articles from the articles directory at compile time."
  []
  (let [articles (metadata/load-all-articles)]
    (reduce (fn [acc {:keys [id metadata content]}]
              (let [title (or (:title metadata) "Untitled Article")
                    description (or (:description metadata) "")
                    date (or (:date metadata) "")
                    tags (if-let [tags-str (:tags metadata)]
                           (map clojure.string/trim
                             (clojure.string/split tags-str #","))
                           [])]
                (assoc acc id (Article. title description date content tags))))
      {}
      articles)))

;; Use the new approach to load articles
(def articles-list (load-articles-at-compile-time))
