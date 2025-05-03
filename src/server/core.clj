(ns server.core
  (:require [clojure.edn :as edn]
            [pohjavirta.server :as server]
            [reitit.ring :as ring]
            [server.middleware.gzip :as gzip]
            [server.middleware.log :as log]
            [server.routes]
            [server.sitemap]))

; Enabling of logs reduces performance significantly
(def config {:log false})

(defn make-app
  [params]
  (ring/ring-handler (ring/router [server.routes/routes
                                   ["/sitemap.xml"
                                    (server.sitemap/gen-sitemap-handler
                                      "https://blog.beldmian.ru"
                                      server.routes/routes)]])
                     (ring/create-default-handler)
                     {:middleware [(if (:log params) log/wrap-request-log [])
                                   gzip/wrap-gzip],
                      :inject-router? false,
                      :inject-match? false}))

(def app (make-app {:log (:log config)}))

(defn -main
  [& _args]
  (-> #'app
      (server/create {:port (edn/read-string (or (System/getenv "PORT")
                                                 "8080")),
                      :host "0.0.0.0"})
      server/start))
