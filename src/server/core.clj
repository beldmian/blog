(ns server.core
  (:gen-class)
  (:require [aleph.http :as aleph]
            [aleph.netty :as netty]
            [clojure.edn :as edn]
            [reitit.ring :as ring]
            [server.middleware.log :as log]
            [server.routes]
            [server.sitemap]))

; Enabling of logs reduces performance significantly
(def config {:log false})

;articles-rotuer

(defn make-app
  [params]
  (ring/ring-handler (ring/router [server.routes/routes
                                   ["/sitemap.xml"
                                    (server.sitemap/gen-sitemap-handler
                                      "https://blog.beldmian.ru"
                                      server.routes/routes)]])
                     {:middleware [(if (:log params) log/wrap-request-log [])],
                      :inject-router? false,
                      :inject-match? false}))

(def app (make-app {:log (:log config)}))


(defn -main
  [& _args]
  (netty/leak-detector-level! :disabled)
  (aleph/start-server #'app
                      {:port (edn/read-string (or (System/getenv "PORT")
                                                  "8080")),
                       :compression? true,
                       :executor :none,
                       :host "0.0.0.0"}))
