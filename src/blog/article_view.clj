(ns blog.article-view
  (:require [blog.articles :refer [articles-list]]
            [ui.md :refer [MarkdownRender]]
            [ui.colors :as colors]
            [ui.responsive :as responsive]))

(defn article-page-meta
  [data]
  (articles-list (-> data
                     :path-params
                     :id)))

(defn render-tags
  [tags]
  (when (seq tags)
    [:div
     {:style {:margin-top (responsive/spacing :md),
              :margin-bottom (responsive/spacing :md)}}
     [:div
      {:style {:font-size "0.9rem",
               :color colors/text-secondary,
               :margin-bottom (responsive/spacing :xs)}} "Tags:"]
     [:div
      {:style
         {:display "flex", :flex-wrap "wrap", :gap (responsive/spacing :xs)}}
      (for [tag tags]
        [:span
         {:style {:background-color colors/surface,
                  :padding
                    (str (responsive/spacing :xs) " " (responsive/spacing :sm)),
                  :border-radius "4px",
                  :font-size "0.8rem",
                  :color colors/text-secondary}} tag])]]))

(defn article-page
  [data]
  (let [article (article-page-meta data)]
    [:div (render-tags (:tags article)) (MarkdownRender (:contents article))]))
