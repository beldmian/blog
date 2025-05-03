;; WARNING: Authored by Gemini 2.5 Pro AI model
(ns server.middleware.cache
  (:import [java.util.concurrent ConcurrentHashMap]))

(defn- cache-key
  [{:keys [uri query-string]}]
  (if query-string (str uri "?" query-string) uri))

(defn- cacheable-response?
  [request response]
  (and response ; Ensure response is not nil
       (= :get (:request-method request))
       (:status response)
       (<= 200 (:status response) 299)))

(defn- expired?
  "Checks if a cached item has expired. Handles potential nil cached-at."
  [cached-item ttl-millis now]
  (if-let [cached-at (:cached-at cached-item)] ; Check if timestamp exists
    (> (- now cached-at) ttl-millis)
    true)) ; Treat items without a timestamp as expired/invalid

(defn wrap-cache
  [handler & [{:keys [ttl], :or {ttl 60}}]]
  (let [^ConcurrentHashMap cache-store (ConcurrentHashMap.)
        ttl-millis (* ttl 1000)]
    (fn [request]
      (let [key (cache-key request)
            now (System/currentTimeMillis)
            computed-value
              (.compute
                cache-store
                key
                (reify
                  java.util.function.BiFunction
                    (apply [_ _k current-value] ; k = key, current-value
                                                ; =
                      ; existing map entry or nil
                      ;; Check if current value exists, has a timestamp,
                      ;; and is not expired
                      (if (and current-value
                               (:cached-at current-value)
                               (not (expired? current-value ttl-millis now)))
                        ;; ---- Cache Hit (Valid) ----
                        current-value ; Return the existing value to keep
                                      ; it in the map
                        ;; ---- Cache Miss or Expired ----
                        (let [response (handler request)] ; Execute the
                                                          ; handler
                          (if (cacheable-response? request response)
                            ;; Cacheable: Return new item to store it
                            {:response response, :cached-at now}
                            ;; Not Cacheable: Return a temporary item
                            ;; with nil timestamp. This item will be
                            ;; stored briefly by .compute
                            {:response response, :cached-at nil}))))))]
        ;; --- Post-.compute processing --- computed-value now holds
        ;; whatever the BiFunction returned
        ;; (which .compute also stored in the map)
        ;; Check if the item stored has a nil timestamp (our marker for
        ;; non-cacheable)
        (if (nil? (:cached-at computed-value))
          ;; It was a non-cacheable response, stored temporarily
          (do (.remove cache-store key) ; Clean up the temporary entry
              (:response computed-value)) ; Return the actual response
                                          ; from the temporary item
          ;; It was a valid hit or a cacheable miss/expiration
          (:response computed-value))) ; Return the response from the
                                       ; (now permanent) map item
    )))
