(ns ui.complex.navigation)

(defn nav_link
  [name href]
  [:a {:href href, :style {:color "#F1FFFA", :margin "0 10px"}} name])

(defn Navigation
  []
  [:div
   {:style {:width "100%",
            :display "flex",
            :justify-items "center",
            :justify-content "center",
            :padding "10px",
            :margin "10px 0",
            :background-color "#464D77",
            :border-radius "10px",
            :color "#F1FFFA"}} (nav_link "home" "/")
   (nav_link "github" "https://github.com/beldmian")
   (nav_link "articles" "/blog") (nav_link "cv" "/cv")])
