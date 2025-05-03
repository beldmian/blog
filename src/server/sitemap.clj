(ns server.sitemap
  (:require [sitemap.core :as sitemap]))

(defn sitemap-entry-from-route
  [public-url [url _data]]
  {:loc (str public-url url)})

(defn gen-sitemap
  [public-url routes]
  (sitemap/generate-sitemap (map #(sitemap-entry-from-route public-url %)
                              routes)))

(defn gen-sitemap-handler
  [public-url routes]
  (fn [_]
    {:body (gen-sitemap public-url routes),
     :status 200,
     :headers {"Content-Type" "application/xml"}}))
