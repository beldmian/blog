;; WARNING: Authored by Gemini 2.5 Pro AI model
(ns server.middleware.log
  (:require [clojure.string :as str])
  (:import [java.util Date]))

(defn wrap-request-log
  "Ring middleware to log request and response details.

  Logs the request method, URI, response status, and processing time.
  Uses println for logging by default.

  Example:
  (def app (wrap-request-log handler))
  "
  [handler]
  (fn [request]
    (let [start-time (System/currentTimeMillis)
          {:keys [request-method uri query-string]} request
          method (str/upper-case (name request-method))
          full-uri (if query-string (str uri "?" query-string) uri)]
      ;; Log incoming request
      (println (format "[%s] >> %s %s" (Date.) method full-uri))
      ;; Process the request
      (let [response (handler request)
            end-time (System/currentTimeMillis)
            duration (- end-time start-time)
            status (:status response)]
        ;; Log response details and duration
        (println
          (format "[%s] << %d %s (%d ms)" (Date.) status full-uri duration))
        ;; Return the original response
        response))))

;; --- Usage Example ---
;; Assuming you have a Ring handler defined like this:
;(defn my-handler [request]
;  (Thread/sleep 50) ; Simulate some work
;  {:status 200
;   :headers {"Content-Type" "text/plain"}
;   :body "Hello World!"})

;; You would wrap it like this:
;(def app (wrap-request-log my-handler))

;; You can also combine it with other middleware:
;; The order matters: Logging usually comes early (outer wrap)
;; to capture the full request lifecycle including other middleware.
;(def app (-> my-handler
;             (wrap-cache {:ttl 10}) ; Inner middleware
;             (wrap-gzip)            ; Middle middleware
;             (wrap-request-log)))   ; Outer middleware

;; Then run your app with a Ring adapter (e.g., http-kit, jetty)
;(require '[org.httpkit.server :as http-kit])
;(http-kit/run-server app {:port 8080})

;; When you access http://localhost:8080, you'll see log lines like:
;; [Tue Apr 29 19:35:01 MSK 2025] >> GET /
;; [Tue Apr 29 19:35:01 MSK 2025] << 200 / (52 ms)
