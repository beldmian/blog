(ns home.view
  (:require [ui.md :refer [MarkdownRender]]
            [macro.core :as mc]))

(defn home-page [] (MarkdownRender (mc/inline-resource "Home.md")))
