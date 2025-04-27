(ns ui.button)

(def styles
  [:.button
   {:transition "all 0.3s",
    :background-color "#464D77",
    :border "none",
    :color "#fff",
    :padding "10px",
    :cursor "pointer",
    :display "inline-block",
    :border-radius "5px"}])

(defmacro Button [text link-href] [:a {:class "button", :href link-href} text])
