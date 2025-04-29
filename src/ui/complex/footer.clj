(ns ui.complex.footer
  (:require [ui.colors :as colors]
            [ui.responsive :as responsive]))

(def styles
  [:.footer
   {:width "100%",
    :margin-top (responsive/spacing :xxl),
    :padding (str (responsive/spacing :lg) " 0"),
    :background-color colors/surface,
    :border-top (str "1px solid " (colors/rgba colors/text-secondary 0.2)),
    :color colors/text-secondary,
    :font-size "0.9rem",
    :text-align "center"}

   ;; Footer content
   [:.footer-content
    {:display "flex",
     :flex-wrap "wrap",
     :justify-content "center",
     :align-items "center",
     :max-width "1200px",
     :margin "0 auto",
     :padding (str "0 " (responsive/spacing :md))}

    ;; Media query for mobile
    [(responsive/media-query-max :xs)
     {:flex-direction "column",
      :text-align "center"}
     [:.footer-section
      {:margin-bottom (responsive/spacing :md)}]]]
   ])

(def Footer
  [:div {:class "footer"}
   [:div {:class "footer-content"}
     [:p "© 2024 beldmian. Built with Clojure and ❤️"]]
    ])
