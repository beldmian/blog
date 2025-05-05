(ns home.view
  (:require [clojure.java.io :as io]
            [ui.md :refer [MarkdownRender]]))

(def home-page (MarkdownRender (slurp (io/resource "Home.md"))))
