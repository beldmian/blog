(ns ui.complex.navigation)

(defmacro nav_link
  [name href is_blank]
  [:a
   {:href href,
    :target (if is_blank "_blank" "_self"),
    :style {:color "#F1FFFA", :margin "0 10px"}} name])

(def Navigation
  [:div
   {:style {:width "100%",
            :display "flex",
            :justify-items "center",
            :justify-content "center",
            :padding "10px",
            :margin "10px 0",
            :background-color "#464D77",
            :border-radius "10px",
            :color "#F1FFFA"}} (nav_link "home" "/" false)
   (nav_link "articles" "/blog" false)
   (nav_link "github" "https://github.com/beldmian" true)
   (nav_link "cv" "/cv" false)])
