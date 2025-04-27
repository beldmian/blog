(ns cv.view)

(defn cv-download-page
  []
  [:div [:a {:href "/public/assets/cv.pdf", :target "_blank"} "CV (russian)"]])
