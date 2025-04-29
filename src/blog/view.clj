(ns blog.view
  (:require [blog.articles :refer [articles-list]]
            [ui.button :refer [Button]]
            [ui.colors :as colors]
            [ui.responsive :as responsive]))

;; Define styles for article cards
(def article-card-styles
  {:padding (responsive/spacing :md),
   :border-radius "8px",
   :background-color colors/surface,
   :width "100%",
   :margin-bottom (responsive/spacing :lg),
   :box-shadow (str "0 2px 8px " (colors/rgba colors/text-primary 0.1)),
   :transition "all 0.3s ease",
   :border-left (str "4px solid " colors/primary),
   :display "flex",
   :flex-direction "column"})

;; Function to render tags
(defn render-article-tags [tags]
  (when (seq tags)
    [:div {:style {:display "flex"
                   :flex-wrap "wrap"
                   :gap (responsive/spacing :xs)
                   :margin-bottom (responsive/spacing :sm)}}
     (for [tag tags]
       [:span {:style {:background-color colors/surface-dark
                       :padding (str (responsive/spacing :xs) " " (responsive/spacing :sm))
                       :border-radius "4px"
                       :font-size "0.8rem"
                       :color colors/text-secondary}}
        tag])]))

;; Function to generate article card with responsive behavior
(defn article-entry
  [[id article]]
  [:div
   {:class "article-card"
    :style article-card-styles}

   ;; Article header
   [:div {:style {:margin-bottom (responsive/spacing :sm)}}
    [:h3 {:style {:font-weight "700"
                  :color colors/primary
                  :margin-top "0"}}
     (:title article)]
    [:p {:style {:color colors/text-secondary
                 :font-size "0.9rem"
                 :margin (str (responsive/spacing :xs) " 0")}}
     [:span {:style {:margin-right (responsive/spacing :xs)}}
      "📅"] (:date article)]]

   ;; Article description
   [:div {:style {:flex "1"}}
    [:p {:style {:margin (str (responsive/spacing :sm) " 0 " (responsive/spacing :md) " 0")
                 :line-height "1.6"}}
     (:description article)]]

   ;; Article tags
   (render-article-tags (:tags article))

   ;; Article footer with button
   [:div {:style {:display "flex"
                  :justify-content "flex-end"
                  :margin-top (responsive/spacing :sm)}}
    (Button "Read Article" (str "/article/" id))]])

;; Blog page
(def blog-page
  [:div
   [:div {:style {:margin-bottom (responsive/spacing :lg)}}
    [:h1 "Articles"]
    [:p "Explore my thoughts, projects, and experiences through these articles."]]

   ;; Articles list
   [:div
    (map article-entry articles-list)]])
