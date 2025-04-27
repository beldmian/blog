(ns server.core
  (:require [pohjavirta.server :as server]
            [ring.middleware.params :as params]
            [reitit.ring :as ring]
            [server.routes]))

(def app
  (ring/ring-handler (ring/router [server.routes/routes]
                                  {:data {:middleware [params/wrap-params]}})
                     (ring/create-default-handler)
                     {:inject-router? false, :inject-match? false}))

(defn -main
  [& _args]
  (let [cpus (.availableProcessors (Runtime/getRuntime))]
    (-> #'app
        (server/create {:port 8080,
                        :host "0.0.0.0",
                        :io-threads (* 2 cpus),
                        :worker-threads (* 8 cpus)})
        server/start)))
