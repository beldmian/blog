(ns ui.button)

(defmacro Button
  [text link-href]
  [:a
   {:style {:transition "all 0.3s",
            ;; :background-color "#202C59",
            :background-color "#464D77",
            :border "none",
            :color "#fff",
            :padding "10px",
            :cursor "pointer",
            :display "inline-block",
            :border-radius "5px"},
    :href link-href} text])
