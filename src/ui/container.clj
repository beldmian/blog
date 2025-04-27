(ns ui.container)

(defn Container
  [& child]
  [:div {:style {:max-width "70%", :margin "0 auto"}} child])

