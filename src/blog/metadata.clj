(ns blog.metadata
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-frontmatter
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
  []
  (let [articles-url (io/resource "articles")]
    (if (= "jar" (.getProtocol articles-url))
      (let [conn (.openConnection articles-url)
            jar (.getJarFile conn)
            entries (enumeration-seq (.entries jar))
            article-entries (filter #(and (not (.isDirectory %))
                                          (.startsWith (.getName %) "articles/")
                                          (.endsWith (.getName %) ".md"))
                              entries)]
        (map #(io/resource (.getName %)) article-entries))
      (let [articles-file (io/file (.toURI articles-url))]
        (filter #(.endsWith (.getName %) ".md") (file-seq articles-file))))))

(defn get-article-id
  [file]
  (let [filename (if (instance? java.io.File file)
                   (.getName file)
                   (last (str/split (str file) #"/")))]
    (str/replace filename #"\.md$" "")))

(defn load-article
  [file]
  (let [content (slurp file)
        {:keys [metadata content]} (parse-frontmatter content)
        id (get-article-id file)]
    {:id id, :metadata metadata, :content content}))

(defn load-all-articles
  []
  (let [files (get-article-files)] (map load-article files)))
