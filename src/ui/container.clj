(ns ui.container
  (:require [ui.responsive :as responsive]
            [ui.colors :as colors]))

(def styles
  [:.container
   {:width "85%",
    :max-width "1200px",
    :margin "0 auto",
    :padding (str (responsive/spacing :lg) " 0"),
    :transition "all 0.3s ease"}
   ;; Media query for tablets
   [(responsive/media-query-max :sm)
    [:.container {:width "90%", :padding (str (responsive/spacing :md) " 0")}]]
   ;; Media query for mobile
   [(responsive/media-query-max :xs)
    [:.container {:width "95%", :padding (str (responsive/spacing :sm) " 0")}]]
   ;; Grid system
   [:.grid
    {:display "grid",
     :grid-template-columns "repeat(12, 1fr)",
     :grid-gap (responsive/spacing :md)}
    ;; Responsive grid adjustments
    [(responsive/media-query-max :sm) {:grid-gap (responsive/spacing :sm)}]
    ;; Grid column spans
    [:.col-1 {:grid-column "span 1"}] [:.col-2 {:grid-column "span 2"}]
    [:.col-3 {:grid-column "span 3"}] [:.col-4 {:grid-column "span 4"}]
    [:.col-5 {:grid-column "span 5"}] [:.col-6 {:grid-column "span 6"}]
    [:.col-7 {:grid-column "span 7"}] [:.col-8 {:grid-column "span 8"}]
    [:.col-9 {:grid-column "span 9"}] [:.col-10 {:grid-column "span 10"}]
    [:.col-11 {:grid-column "span 11"}] [:.col-12 {:grid-column "span 12"}]
    ;; Responsive column spans for tablets
    [(responsive/media-query-max :sm) [:.col-sm-1 {:grid-column "span 1"}]
     [:.col-sm-2 {:grid-column "span 2"}] [:.col-sm-3 {:grid-column "span 3"}]
     [:.col-sm-4 {:grid-column "span 4"}] [:.col-sm-5 {:grid-column "span 5"}]
     [:.col-sm-6 {:grid-column "span 6"}] [:.col-sm-7 {:grid-column "span 7"}]
     [:.col-sm-8 {:grid-column "span 8"}] [:.col-sm-9 {:grid-column "span 9"}]
     [:.col-sm-10 {:grid-column "span 10"}]
     [:.col-sm-11 {:grid-column "span 11"}]
     [:.col-sm-12 {:grid-column "span 12"}]]
    ;; Responsive column spans for mobile
    [(responsive/media-query-max :xs) [:.col-xs-1 {:grid-column "span 1"}]
     [:.col-xs-2 {:grid-column "span 2"}] [:.col-xs-3 {:grid-column "span 3"}]
     [:.col-xs-4 {:grid-column "span 4"}] [:.col-xs-5 {:grid-column "span 5"}]
     [:.col-xs-6 {:grid-column "span 6"}] [:.col-xs-7 {:grid-column "span 7"}]
     [:.col-xs-8 {:grid-column "span 8"}] [:.col-xs-9 {:grid-column "span 9"}]
     [:.col-xs-10 {:grid-column "span 10"}]
     [:.col-xs-11 {:grid-column "span 11"}]
     [:.col-xs-12 {:grid-column "span 12"}]]]
   ;; Card component
   [:.card
    {:background-color colors/surface,
     :border-radius "8px",
     :padding (responsive/spacing :md),
     :box-shadow (str "0 2px 8px " (colors/rgba colors/text-primary 0.1)),
     :transition "all 0.3s ease"}
    [:&:hover
     {:box-shadow (str "0 4px 12px " (colors/rgba colors/text-primary 0.15))}]
    [(responsive/media-query-max :xs) {:padding (responsive/spacing :sm)}]]])

;; Container component
(defn Container [& child] [:div {:class "container"} child])

