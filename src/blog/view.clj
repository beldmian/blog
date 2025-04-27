(ns blog.view
  (:require [blog.articles :refer [articles-list]]
            [ui.button :refer [Button]]))

(defn article-entry
  [[id article]]
  [:div
   {:style {:padding "10px",
            :border-radius "10px",
            :background-color "#d0d4e8",
            :width "100%",
            :margin-bottom "10px"}}
   [:h3 {:style {:font-weight "700"}} (:title article)]
   [:p {:style {:color "#3d3d3d"}} (:date article)] [:p (:description article)]
   (Button "read" (str "/article/" id))])

(defn blog-page [] [:div (map article-entry articles-list)])
