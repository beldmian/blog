(ns ui.colors)

;; Color palette
(def primary "#3949AB")       ;; Deep blue
(def primary-light "#6F74DD") ;; Lighter blue
(def primary-dark "#00227B")  ;; Darker blue

(def background "#FFFFFF")    ;; White
(def surface "#F5F7FA")       ;; Light gray for cards/surfaces

(def text-primary "#212121")  ;; Almost black
(def text-secondary "#757575") ;; Medium gray
(def text-on-primary "#FFFFFF") ;; White text on primary color

;; Function to get rgba version of a color
(defn rgba
  [hex opacity]
  (str "rgba("
       (Integer/parseInt (subs hex 1 3) 16)
       ", "
       (Integer/parseInt (subs hex 3 5) 16)
       ", "
       (Integer/parseInt (subs hex 5 7) 16)
       ", "
       opacity
       "))"))
