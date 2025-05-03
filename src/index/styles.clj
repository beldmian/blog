(ns index.styles
  (:require [garden.core :refer [css]]
            [ui.styles :as ui]
            [ui.colors :as colors]
            [ui.responsive :as responsive]))

(def base-styles
  [:*
   {:box-sizing "border-box",
    :font-family "'Roboto', sans-serif",
    :font-optical-sizing "auto",
    :font-weight "400",
    :font-style "normal",
    :color colors/text-primary}])

;; Responsive typography
(def typography
  [;; Headings
   [:h1
    {:font-size (get-in responsive/responsive-font-sizes [:h1 :xs]),
     :font-weight "700",
     :margin (str (responsive/spacing :lg) " 0 " (responsive/spacing :md) " 0"),
     :color colors/primary,
     :line-height "1.2"}]
   [(responsive/media-query-min :sm)
    [:h1 {:font-size (get-in responsive/responsive-font-sizes [:h1 :sm])}]]
   [(responsive/media-query-min :md)
    [:h1 {:font-size (get-in responsive/responsive-font-sizes [:h1 :md])}]]
   [:h2
    {:font-size (get-in responsive/responsive-font-sizes [:h2 :xs]),
     :font-weight "700",
     :margin (str (responsive/spacing :md) " 0 " (responsive/spacing :sm) " 0"),
     :color colors/primary,
     :line-height "1.3"}]
   [(responsive/media-query-min :sm)
    [:h2 {:font-size (get-in responsive/responsive-font-sizes [:h2 :sm])}]]
   [(responsive/media-query-min :md)
    [:h2 {:font-size (get-in responsive/responsive-font-sizes [:h2 :md])}]]
   [:h3
    {:font-size (get-in responsive/responsive-font-sizes [:h3 :xs]),
     :font-weight "600",
     :margin (str (responsive/spacing :md) " 0 " (responsive/spacing :sm) " 0"),
     :color colors/primary,
     :line-height "1.4"}]
   [(responsive/media-query-min :sm)
    [:h3 {:font-size (get-in responsive/responsive-font-sizes [:h3 :sm])}]]
   [(responsive/media-query-min :md)
    [:h3 {:font-size (get-in responsive/responsive-font-sizes [:h3 :md])}]]
   [:h4
    {:font-size (get-in responsive/responsive-font-sizes [:h4 :xs]),
     :font-weight "600",
     :margin (str (responsive/spacing :sm) " 0 " (responsive/spacing :sm) " 0"),
     :color colors/primary,
     :line-height "1.4"}]
   [(responsive/media-query-min :sm)
    [:h4 {:font-size (get-in responsive/responsive-font-sizes [:h4 :sm])}]]
   [(responsive/media-query-min :md)
    [:h4 {:font-size (get-in responsive/responsive-font-sizes [:h4 :md])}]]
   ;; Paragraph and text elements
   [:p
    {:line-height "1.6",
     :margin (str "0 0 " (responsive/spacing :md) " 0"),
     :font-size (get-in responsive/responsive-font-sizes [:body :xs])}]
   [(responsive/media-query-min :sm)
    [:p {:font-size (get-in responsive/responsive-font-sizes [:body :sm])}]]
   ;; Links
   [:a
    {:color colors/primary,
     :text-decoration "none",
     :transition "all 0.2s ease",
     :position "relative"}
    [:&:hover {:color colors/primary-light, :text-decoration "underline"}]]
   ;; Code blocks
   [:code
    {:font-family "monospace",
     :background-color colors/surface,
     :padding (str (responsive/spacing :xs) " " (responsive/spacing :sm)),
     :border-radius "3px",
     :font-size "0.9em"}]
   [:pre
    {:background-color colors/surface,
     :padding (responsive/spacing :md),
     :border-radius "5px",
     :overflow "auto",
     :margin (str (responsive/spacing :md) " 0"),
     :box-shadow (str "0 2px 4px " (colors/rgba colors/text-primary 0.05))}
    [:code {:background "none", :padding "0"}]]
   ;; Blockquotes
   [:blockquote
    {:border-left (str "4px solid " colors/primary-light),
     :padding-left (responsive/spacing :md),
     :margin (str (responsive/spacing :md) " 0"),
     :color colors/text-secondary,
     :font-style "italic"}]
   ;; Lists
   [:ul
    {:padding-left (responsive/spacing :xl),
     :margin (str (responsive/spacing :md) " 0")}
    [:li
     {:margin (str (responsive/spacing :xs) " 0"),
      :padding (str "0 0 " (responsive/spacing :xs) " 0")}]]
   [:ol
    {:padding-left (responsive/spacing :xl),
     :margin (str (responsive/spacing :md) " 0")}
    [:li
     {:margin (str (responsive/spacing :xs) " 0"),
      :padding (str "0 0 " (responsive/spacing :xs) " 0")}]]
   ;; Images
   [:img
    {:max-width "100%",
     :height "auto",
     :border-radius "5px",
     :margin (str (responsive/spacing :md) " 0"),
     :box-shadow (str "0 3px 10px " (colors/rgba colors/text-primary 0.1))}]])

;; Global styles
(def global-styles
  [[:html {:font-size "16px", :scroll-behavior "smooth"}]
   [:body
    {:background-color colors/background,
     :line-height "1.6",
     :overflow-x "hidden",
     :margin "0",
     :padding "0"}]
   ;; Responsive adjustments
   [(responsive/media-query-max :xs) [:html {:font-size "14px"}]]
   ;; Selection styling
   ["::selection"
    {:background-color colors/primary-light, :color colors/text-on-primary}]])

(def all_styles [base-styles typography global-styles])

(def styles (css {:pretty-print? false} [all_styles ui/styles]))

