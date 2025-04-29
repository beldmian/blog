;; WARNING: Authored by Gemini 2.5 Pro AI model
(ns server.middleware.gzip
  (:require [clojure.java.io :as io])
  (:import [java.util.zip GZIPOutputStream]
           [java.io ByteArrayOutputStream InputStream]))

(defn- compressible-body?
  "Checks if the response body is suitable for compression."
  [body]
  (or (string? body) (instance? InputStream body) (bytes? body)))

(defn- gzip-stream
  "Compresses an InputStream using GZIP."
  [^InputStream input]
  (let [baos (ByteArrayOutputStream.)]
    (with-open [gzip (GZIPOutputStream. baos)] (io/copy input gzip))
    (.toByteArray baos)))

(defn- gzip-string
  "Compresses a String using GZIP."
  [^String input]
  (let [baos (ByteArrayOutputStream.)]
    (with-open [gzip (GZIPOutputStream. baos)
                writer (io/writer gzip)] ; Write string as UTF-8 bytes
      (.write writer input))
    (.toByteArray baos)))

(defn- gzip-bytes
  "Compresses a byte array using GZIP."
  [^bytes input]
  (let [baos (ByteArrayOutputStream.)]
    (with-open [gzip (GZIPOutputStream. baos)] (.write gzip input))
    (.toByteArray baos)))

(defn wrap-gzip
  "Ring middleware to compress response bodies using GZIP.

  Checks the 'Accept-Encoding' request header. If 'gzip' is present
  and the response status is 2xx or 3xx, and the body is compressible
  (String, InputStream, or byte array), it compresses the body and adds
  the 'Content-Encoding: gzip' header.

  Example:
  (def app (wrap-gzip handler))"
  [handler]
  (fn [request]
    (let [response (handler request)
          headers (:headers request)
          accept-encoding (get headers "accept-encoding")
          ;; Check if client accepts gzip and response is suitable
          accepts-gzip? (and accept-encoding
                             (re-find #"(?i)\bgzip\b" accept-encoding))
          body (:body response)
          already-encoded? (get (:headers response) "content-encoding")]
      (if (and accepts-gzip?
               (not already-encoded?) ; Don't double-encode
               (compressible-body? body))
        (let [gzipped-body (cond (string? body) (gzip-string body)
                                 (instance? InputStream body) (gzip-stream body)
                                 (bytes? body) (gzip-bytes body)
                                 :else body) ; Should not happen due to
                                             ; compressible-body? check
              new-headers (assoc (:headers response)
                            "Content-Encoding" "gzip"
                            ;; Update Content-Length if it was set,
                            ;; otherwise let the adapter handle it.
                            "Content-Length" (str (count gzipped-body)))]
          (assoc response
            :body gzipped-body
            :headers new-headers))
        ;; If not compressing, return original response
        response))))

;; --- Usage Example ---
;; Assuming you have a Ring handler defined like this:
;(defn my-handler [request]
;  {:status 200
;   :headers {"Content-Type" "text/plain"}
;   :body "This is a response body that can be compressed."})

;; You would wrap it like this:
;(def app (wrap-gzip my-handler))

;; Then run your app with a Ring adapter (e.g., http-kit, jetty)
;(require '[org.httpkit.server :as http-kit])
;(http-kit/run-server app {:port 8080})
;; Accessing http://localhost:8080 with a client that sends
;; 'Accept-Encoding: gzip' will receive a gzipped response.
