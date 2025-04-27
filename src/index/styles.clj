(ns index.styles
  (:require [garden.core :refer [css]]
            [ui.styles :as ui]))

(def all_styles
  [:*
   {:font-family "'Roboto', sans-serif",
    :font-optical-sizing "auto",
    :font-weight "400",
    :font-style "normal",
    :color "#111D13"}])

(def styles (css {:pretty-print? false} [all_styles ui/styles]))

