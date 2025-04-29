(ns blog.articles
  (:require [macro.core :as mc]
            [blog.metadata :as metadata]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defrecord Article [title description date contents tags])

;; Function to create an Article from a file with frontmatter
(defn create-article-from-file
  "Create an Article record from a markdown file with frontmatter.
   Falls back to default values if metadata is missing."
  [file]
  (let [content (slurp file)
        {:keys [metadata content]} (metadata/parse-frontmatter content)
        title (or (:title metadata) "Untitled Article")
        description (or (:description metadata) "")
        date (or (:date metadata) "")
        tags (if-let [tags-str (:tags metadata)]
               (map str/trim (str/split tags-str #","))
               [])]
    (Article. title description date content tags)))

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
                           (map clojure.string/trim (clojure.string/split tags-str #","))
                           [])]
                (assoc acc id (Article. title description date content tags))))
            {}
            articles)))

;; Legacy approach - manually defined articles
(def articles-list-legacy
  {"yastation"
     (Article.
       "Creating yastation - a console client for managing Yandex Station"
       "One day, while sitting at work and listening to music on my Yandex Station, I discovered something interesting - I couldn't simply skip the track. You might ask, \"How could that happen?\" I'd reply that yesterday I lost my voice during a heated political debate, and my phone, inconveniently, was further away than I could reach. Ultimately, this unfortunate situation prompted me to create my own solution"
       "03/27/2022"
       (mc/inline-resource "articles/yastation.md")
       [])})

;; Use the new approach to load articles
(def articles-list (load-articles-at-compile-time))
