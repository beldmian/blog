(ns server.core
  (:require [pohjavirta.server :as server]
            [ring.middleware.params :as params]
            [reitit.ring :as ring]
            [ring.middleware.gzip]
            [server.routes]))

(def app
  (ring/ring-handler (ring/router [server.routes/routes]
                                  {:data {:middleware
                                            [ring.middleware.gzip/wrap-gzip
                                             params/wrap-params]}})
                     (ring/create-default-handler)
                     {:inject-router? false}))

(defn -main
  [& _args]
  (let [cpus (.availableProcessors (Runtime/getRuntime))]
    (-> #'app
        (server/create {:port 8080,
                        :host "0.0.0.0",
                        :io-threads (* 2 cpus),
                        :worker-threads (* 8 cpus)})
        server/start)))
