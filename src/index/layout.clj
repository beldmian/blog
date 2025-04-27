(ns index.layout
  (:require [hiccup2.core :as h]
            [macro.core :as mc]
            [ui.complex.navigation :refer [Navigation]]
            [ui.container :refer [Container]]))

(defn Layout
  [page-meta & children]
  [:html {:lang "en"}
   [:head [:meta {:charset "UTF-8"}]
    [:style (h/raw (mc/inline-resource "public/css/styles.css"))]
    [:style (h/raw (mc/inline-resource "public/css/normalize.css"))]
    [:title (:title page-meta)]
    [:meta {:name "description", :content (:description page-meta)}]
    [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]]
   [:body (Container (Navigation) children)]])
