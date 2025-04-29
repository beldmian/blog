(ns server.core
  (:require [pohjavirta.server :as server]
            [reitit.ring :as ring]
            [server.cache :as cache]
            [server.gzip :as gzip]
            [server.routes]))

(def app
  (ring/ring-handler (ring/router [server.routes/routes])
                     (ring/create-default-handler)
                     {:middleware [cache/wrap-cache gzip/wrap-gzip],
                      :inject-router? false,
                      :inject-match? false}))

(defn -main
  [& _args]
  (let [cpus (.availableProcessors (Runtime/getRuntime))]
    (-> #'app
        (server/create {:port 8080,
                        :host "0.0.0.0",
                        :io-threads (* 2 cpus),
                        :worker-threads (* 8 cpus)})
        server/start)))
