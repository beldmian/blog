(ns server.core
  (:require [clojure.edn :as edn]
            [pohjavirta.server :as server]
            [reitit.ring :as ring]
            [server.middleware.cache :as cache]
            [server.middleware.gzip :as gzip]
            [server.middleware.log :as log]
            [server.routes]))

(defn make-app
  [params]
  (ring/ring-handler (ring/router [server.routes/routes])
                     (ring/create-default-handler)
                     {:middleware [(if (:log params) log/wrap-request-log [])
                                   cache/wrap-cache gzip/wrap-gzip],
                      :inject-router? false,
                      :inject-match? false}))

; Enabling of logs reduces performance significantly
(def app (make-app {:log false}))

(defn -main
  [& _args]
  (let [cpus (.availableProcessors (Runtime/getRuntime))]
    (-> #'app
        (server/create {:port (edn/read-string (or (System/getenv "PORT")
                                                   "8080")),
                        :host "0.0.0.0",
                        :io-threads (* 2 cpus),
                        :worker-threads (* 8 cpus)})
        server/start)))
