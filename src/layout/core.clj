(ns layout.core
  (:require [clojure.java.io :as io]
            [hiccup2.core :as h]
            [layout.styles :as styles]
            [ui.complex.footer :refer [Footer]]
            [ui.complex.navigation :refer [Navigation]]
            [ui.container :refer [Container]]))

(defn Layout
  [page-meta & children]
  [:html {:lang "en"}
   [:head [:meta {:charset "UTF-8"}] [:style (h/raw styles/styles)]
    [:style (h/raw (slurp (io/resource "public/css/base.css")))]
    [:title (:title page-meta)]
    [:meta {:name "description", :content (:description page-meta)}]
    [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]]
   [:body
    [:div
     {:style {:min-height "100vh", :display "flex", :flex-direction "column"}}
     [:div {:style {:flex "1"}} (Container Navigation children)] Footer]]])
