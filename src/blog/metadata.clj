(ns blog.metadata
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-frontmatter
  "Parse YAML-like frontmatter from markdown content.
   Returns a map with :metadata and :content keys."
  [content]
  (if (str/starts-with? content "---")
    (let [parts (str/split content #"---" 3)]
      (if (>= (count parts) 3)
        (let [frontmatter (nth parts 1)
              content (nth parts 2)
              metadata-lines (str/split-lines frontmatter)
              metadata-map (reduce
                            (fn [acc line]
                              (if-let [[_ key value] (re-matches #"([^:]+):\s*(.*)" line)]
                                (assoc acc (keyword (str/trim key)) (str/trim value))
                                acc))
                            {}
                            metadata-lines)]
          {:metadata metadata-map
           :content content})
        {:metadata {} :content content}))
    {:metadata {} :content content}))

(defn get-article-files
  "Get a list of all markdown files in the articles directory."
  []
  (let [articles-url (io/resource "articles")
        articles-file (io/file (.toURI articles-url))]
    (filter #(.endsWith (.getName %) ".md")
            (file-seq articles-file))))

(defn get-article-id
  "Extract article ID from filename (without extension)."
  [file]
  (let [filename (.getName file)]
    (str/replace filename #"\.md$" "")))

(defn load-article
  "Load an article file and parse its frontmatter.
   Returns a map with :id, :metadata, and :content keys."
  [file]
  (let [content (slurp file)
        {:keys [metadata content]} (parse-frontmatter content)
        id (get-article-id file)]
    {:id id
     :metadata metadata
     :content content}))

(defn load-all-articles
  "Load all articles with their metadata."
  []
  (let [files (get-article-files)]
    (map load-article files)))
