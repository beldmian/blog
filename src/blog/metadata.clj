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
              metadata-map
                (reduce (fn [acc line]
                          (if-let [[_ key value] (re-matches #"([^:]+):\s*(.*)"
                                                             line)]
                            (assoc acc
                              (keyword (str/trim key)) (str/trim value))
                            acc))
                  {}
                  metadata-lines)]
          {:metadata metadata-map, :content content})
        {:metadata {}, :content content}))
    {:metadata {}, :content content}))

(defn get-article-files
  "Get a list of all markdown files in the articles directory.
   Works both with filesystem resources and JAR resources."
  []
  (let [articles-url (io/resource "articles")]
    (if (= "jar" (.getProtocol articles-url))
      ;; Handle JAR resources
      (let [conn (.openConnection articles-url)
            jar (.getJarFile conn)
            entries (enumeration-seq (.entries jar))
            article-entries (filter #(and (not (.isDirectory %))
                                          (.startsWith (.getName %) "articles/")
                                          (.endsWith (.getName %) ".md"))
                              entries)]
        (map #(io/resource (.getName %)) article-entries))
      ;; Handle filesystem resources
      (let [articles-file (io/file (.toURI articles-url))]
        (filter #(.endsWith (.getName %) ".md") (file-seq articles-file))))))

(defn get-article-id
  "Extract article ID from filename (without extension)."
  [file]
  (let [filename (if (instance? java.io.File file)
                   (.getName file)
                   (last (str/split (str file) #"/")))]
    (str/replace filename #"\.md$" "")))

(defn load-article
  "Load an article file and parse its frontmatter.
   Returns a map with :id, :metadata, and :content keys."
  [file]
  (let [content (slurp file)
        {:keys [metadata content]} (parse-frontmatter content)
        id (get-article-id file)]
    {:id id, :metadata metadata, :content content}))

(defn load-all-articles
  "Load all articles with their metadata."
  []
  (let [files (get-article-files)] (map load-article files)))
