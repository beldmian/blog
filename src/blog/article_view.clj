(ns blog.article-view
  (:require [blog.articles :refer [articles-list]]
            [ui.md :refer [MarkdownRender]]))

(defn article-page-meta
  [data]
  (articles-list (-> data
                     :path-params
                     :id)))

(defn article-page [data] (MarkdownRender (:contents (article-page-meta data))))

