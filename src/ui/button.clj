(ns ui.button
  (:require [ui.colors :as colors]))

(def styles
  [:.button
   {:transition "all 0.3s ease",
    :background-color colors/primary,
    :border "none",
    :color colors/text-on-primary,
    :padding "12px 20px",
    :cursor "pointer",
    :display "inline-block",
    :border-radius "6px",
    :font-weight "500",
    :text-decoration "none",
    :text-align "center",
    :box-shadow (str "0 2px 4px " (colors/rgba colors/primary-dark 0.2))}
   [:&:hover
    {:background-color colors/primary-light,
     :color "white",
     :transform "translateY(-2px)",
     :box-shadow (str "0 4px 8px " (colors/rgba colors/primary-dark 0.3))}]
   [:&:active
    {:transform "translateY(0)",
     :box-shadow (str "0 1px 2px " (colors/rgba colors/primary-dark 0.2))}]])

(defmacro Button [text link-href] [:a {:class "button", :href link-href} text])
