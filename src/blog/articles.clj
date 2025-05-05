(ns blog.articles
  (:require [blog.metadata :as metadata]
            [clojure.string :as str]
            [hiccup2.core :as h]
            [layout.core :refer [Layout]]
            [ui.md :refer [MarkdownRender]]))

(defrecord Article [title description date contents tags])

(defn load-articles-at-compile-time
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

(def articles-list (load-articles-at-compile-time))

(defn article-page-static [article] [:div (MarkdownRender (:contents article))])
(defn make-article-page
  [[id article]]
  {id (str (h/html (h/raw "<!DOCTYPE html>")
                   (Layout article (article-page-static article))))})

(def article-pages (apply merge (map make-article-page articles-list)))
