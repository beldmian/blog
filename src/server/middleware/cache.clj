;; WARNING: Authored by Gemini 2.5 Pro AI model
(ns server.middleware.cache)

(defn- cache-key
  "Generates a cache key from the request map.
  Uses :uri and :query-string."
  [{:keys [uri query-string], :as _request}]
  (if query-string (str uri "?" query-string) uri))

(defn- cacheable-response?
  "Checks if a response is suitable for caching.
  Must be a GET request with a 200 OK status."
  [request response]
  (and (= :get (:request-method request)) (= 200 (:status response))))

(defn- expired?
  "Checks if a cached item has expired."
  [cached-item ttl-millis]
  (let [cached-at (:cached-at cached-item)
        now (System/currentTimeMillis)]
    (> (- now cached-at) ttl-millis)))

(defn wrap-cache
  "Ring middleware for simple in-memory response caching.

  Caches successful GET requests for a specified time-to-live (TTL).

  Options:
    :ttl - Time-to-live in seconds (default: 60)
    :cache-store - An atom containing the cache map (defaults to a new atom)

  Example:
  (def app (wrap-cache handler {:ttl 300})) ; Cache for 5 minutes
  "
  [handler & [{:keys [ttl cache-store], :or {ttl 60}}]]
  (let [cache (or cache-store (atom {})) ; Use provided atom or create a
                                         ; new one
        ttl-millis (* ttl 1000)]
    (fn [request]
      (let [key (cache-key request)]
        (if-let [cached-item (get @cache key)]
          ;; Cache hit
          (if (expired? cached-item ttl-millis)
            ;; Expired: Fetch, cache, and return new response
            (let [response (handler request)]
              (when (cacheable-response? request response)
                (swap! cache assoc
                  key
                  {:response response, :cached-at (System/currentTimeMillis)}))
              response)
            ;; Not expired: Return cached response
            (:response cached-item))
          ;; Cache miss: Fetch, cache (if applicable), and return
          (let [response (handler request)]
            (when (cacheable-response? request response)
              (swap! cache assoc
                key
                {:response response, :cached-at (System/currentTimeMillis)}))
            response))))))

;; --- Usage Example ---
;; Assuming you have a Ring handler defined like this:
;(defn time-handler [request]
;  {:status 200
;   :headers {"Content-Type" "text/plain"}
;   :body (str "Current time: " (Date.))}) ; Body changes with each call

;; You would wrap it like this:
;(def app (wrap-cache time-handler {:ttl 10})) ; Cache responses for 10 seconds

;; You can also combine it with the gzip middleware (order matters):
;(def app (-> time-handler
;             (wrap-cache {:ttl 10})
;             (wrap-gzip))) ; Apply gzip *after* caching the original response

;; Then run your app with a Ring adapter (e.g., http-kit, jetty)
;(require '[org.httpkit.server :as http-kit])
;(http-kit/run-server app {:port 8080})
;; Accessing http://localhost:8080 repeatedly within 10 seconds
;; will return the same cached time response. After 10 seconds,
;; a new response will be generated and cached.
