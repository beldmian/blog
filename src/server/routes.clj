(ns server.routes
  (:require [blog.articles :refer [article-pages articles-list]]
            [blog.view :refer [blog-page]]
            [hiccup2.core :as h]
            [home.view :refer [home-page]]
            [layout.core :refer [Layout]]
            [ring.util.response :as resp]
            [server.middleware.cache :as cache]
            [server.rss :refer [make-rss-feed-handler]]
            [clojure.core :as c]))

(defn make_page_handler
  [metadata page]
  (fn [_]
    {:status 200,
     :body (str (h/html (h/raw "<!DOCTYPE html>") (Layout metadata page)))}))

(def index_get
  (make_page_handler {:title "beldmian's blog", :description ""} home-page))

(def blog_get
  (make_page_handler {:title "beldmian's blog articles", :description ""}
                     blog-page))

(defn make_article_route
  [[id content]]
  [(format "/article/%s" id) (c/constantly {:status 200, :body content})])

(def article-routes (map make_article_route article-pages))

(defn static_handler
  [request]
  (resp/header (resp/resource-response (:uri request))
               "Cache-Control"
               "max-age=31536000"))

(defn robots_handler
  [_req]
  (resp/header (resp/resource-response "public/robots.txt")
               "Cache-Control"
               "max-age=31536000"))

(defn favicon_handler
  [_req]
  (resp/header (resp/resource-response "public/favicon.ico")
               "Cache-Control"
               "max-age=31536000"))

(def routes
  (into []
        (concat (map #(vector (nth % 0) (cache/wrap-cache (nth % 1)))
                  [["/" index_get] ["/blog" blog_get]
                   ["/rss" (make-rss-feed-handler articles-list)]
                   ["/public/*" static_handler] ["/robots.txt" robots_handler]
                   ["/favicon.ico" favicon_handler]])
                article-routes)))
