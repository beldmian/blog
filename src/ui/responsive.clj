(ns ui.responsive)

;; Breakpoints
(def breakpoints
  {:xs 480,  ;; Mobile phones
   :sm 768,  ;; Tablets
   :md 992,  ;; Small laptops
   :lg 1200, ;; Desktops
   :xl 1600  ;; Large screens
  })

;; Media query helpers
(defn media-query-min
  [breakpoint]
  (str "@media (min-width: " (get breakpoints breakpoint) "px)"))

(defn media-query-max
  [breakpoint]
  (str "@media (max-width: " (get breakpoints breakpoint) "px)"))

;; Responsive font sizes
(def responsive-font-sizes
  {:h1 {:xs "1.8rem", :sm "2.2rem", :md "2.5rem"},
   :h2 {:xs "1.5rem", :sm "1.8rem", :md "2rem"},
   :h3 {:xs "1.3rem", :sm "1.4rem", :md "1.5rem"},
   :h4 {:xs "1.1rem", :sm "1.15rem", :md "1.2rem"},
   :body {:xs "0.95rem", :sm "1rem", :md "1rem"},
   :small {:xs "0.8rem", :sm "0.85rem", :md "0.9rem"}})

;; Spacing scale (in rem)
(def spacing-scale {:xs 0.25, :sm 0.5, :md 1, :lg 1.5, :xl 2, :xxl 3})

;; Helper function to get spacing value
(defn spacing [size] (str (get spacing-scale size) "rem"))
