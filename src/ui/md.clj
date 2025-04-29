(ns ui.md
  (:require [hiccup2.core :as h]
            [markdown.core :refer [md-to-html-string]]
            [blog.metadata :as metadata]))

(defn MarkdownRender
  "Render markdown content to HTML. If the content contains frontmatter,
   it will be stripped before rendering."
  [md_string]
  (let [{:keys [content]} (metadata/parse-frontmatter md_string)]
    (h/raw (md-to-html-string content))))
