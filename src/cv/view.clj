(ns cv.view
  (:require [ui.button :refer [Button]]
            [ui.colors :as colors]
            [ui.responsive :as responsive]))

(def cv-download-page
  [:div [:h1 "Curriculum Vitae"]
   [:p
    "Download my CV to learn more about my professional experience, skills, and education."]
   ;; CV download card
   [:div
    {:style {:margin (str (responsive/spacing :lg) " 0"),
             :padding (responsive/spacing :lg),
             :background-color colors/surface,
             :border-radius "8px",
             :text-align "center",
             :box-shadow (str "0 2px 8px "
                              (colors/rgba colors/text-primary 0.1)),
             :transition "all 0.3s ease"},
     :class "card"}
    ;; CV icon
    [:div {:style {:margin-bottom (responsive/spacing :md)}}
     [:span {:style {:font-size "3rem", :color colors/primary}} "📄"]]
    [:h2 {:style {:margin-bottom (responsive/spacing :md)}} "Download My CV"]
    [:p
     {:style {:font-size "1.2rem",
              :margin-bottom (responsive/spacing :md),
              :max-width "600px",
              :margin-left "auto",
              :margin-right "auto"}}
     "My CV is available for download in PDF format. It contains detailed information about my skills, experience, and education."]
    ;; Download button
    [:div
     {:style {:margin-top (responsive/spacing :lg),
              :display "flex",
              :gap (responsive/spacing :md),
              :align-items "center",
              :justify-content "center"}}
     (Button "Download CV (Russian)" "/public/assets/cv_ru.pdf")
     (Button "Download CV (English)" "/public/assets/cv_en.pdf")]
    ;; Additional information
    [:p
     {:style {:margin-top (responsive/spacing :md),
              :font-size "0.9rem",
              :color colors/text-secondary}}
     "The CV is in PDF format and may open in a new tab depending on your browser settings."]]])
