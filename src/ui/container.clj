(ns ui.container)

(def styles [:.container {:width "70%", :margin "0 auto"}])

(defn Container [& child] [:div {:class "container"} child])

