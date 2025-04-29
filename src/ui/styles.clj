(ns ui.styles
  (:require [ui.button :as button]
            [ui.complex.navigation :as navigation]
            [ui.complex.footer :as footer]
            [ui.container :as container]))

(def styles [button/styles container/styles navigation/styles footer/styles])
