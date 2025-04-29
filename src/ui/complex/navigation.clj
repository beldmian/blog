(ns ui.complex.navigation
  (:require [ui.colors :as colors]
            [ui.responsive :as responsive]))

(def styles
  [:.nav
   {:width "100%",
    :display "flex",
    :flex-wrap "wrap",
    :justify-content "center",
    :align-items "center",
    :padding (responsive/spacing :md),
    :margin (str "0 0 " (responsive/spacing :xl) " 0"),
    :background-color colors/primary,
    :border-radius "8px",
    :box-shadow (str "0 2px 4px " (colors/rgba colors/primary-dark 0.2)),
    :color colors/text-on-primary}

   ;; Navigation links
   [:a {:color colors/text-on-primary,
        :margin (str (responsive/spacing :xs) " " (responsive/spacing :md)),
        :text-decoration "none",
        :font-weight "500",
        :font-size "1.1rem",
        :position "relative",
        :transition "all 0.3s ease"}
    [:&:hover {:transform "translateY(-2px)"}
     [:&:after {:transform "scaleX(1)"}]]
    [:&:after {:content "\"\"",
               :position "absolute",
               :bottom "-5px",
               :left "0",
               :width "100%",
               :height "2px",
               :background-color colors/text-on-primary,
               :transform "scaleX(0)",
               :transform-origin "bottom right",
               :transition "transform 0.3s ease"}]]

   ;; Media query for tablet
   [(responsive/media-query-max :sm)
    {:padding (responsive/spacing :sm)}
    [:a {:font-size "1rem",
         :margin (str (responsive/spacing :xs) " " (responsive/spacing :sm))}]]

   ;; Media query for mobile
   [(responsive/media-query-max :xs)
    {:flex-direction "column",
     :padding (str (responsive/spacing :md) " " (responsive/spacing :sm))}
    [:a {:margin (str (responsive/spacing :sm) " 0"),
         :font-size "1rem"}]]])

(defmacro nav_link
  [name href is_blank]
  [:a {:href href, :target (if is_blank "_blank" "_self")} name])

(def Navigation
  [:div {:class "nav"}
   (nav_link "Home" "/" false)
   (nav_link "Articles" "/blog" false)
   (nav_link "GitHub" "https://github.com/beldmian" true)
   (nav_link "CV" "/cv" false)])
