(ns ui.md
  (:require [hiccup2.core :as h]
            [markdown.core :refer [md-to-html-string]]))

(defn MarkdownRender [md_string] [:div (h/raw (md-to-html-string md_string))])
