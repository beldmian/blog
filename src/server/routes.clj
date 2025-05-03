(ns server.routes
  (:require [blog.article-view :refer [article-page article-page-meta]]
            [blog.articles :refer [articles-list]]
            [blog.view :refer [blog-page]]
            [cv.view :refer [cv-download-page]]
            [hiccup2.core :as h]
            [home.view :refer [home-page]]
            [index.layout :refer [Layout]]
            [ring.util.response :as resp]))

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

(def cv_get
  (make_page_handler {:title "beldmian's cv", :description ""}
                     cv-download-page))

(defn article_get
  [req]
  {:status 200,
   :body (str (h/html (h/raw "<!DOCTYPE html>")
                      (Layout (article-page-meta req) (article-page req))))})

(defn make_article_route
  [[id _article]]
  [(format "/article/%s" id) (fn [_] (article_get {:path-params {:id id}}))])

(defn make_articles_router [articles] (map make_article_route articles))

(def article-routes (make_articles_router articles-list))

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

(def routes
  (into []
        (concat [["/" index_get] ["/blog" blog_get] ["/cv" cv_get]
                 ["/public/*path" static_handler]
                 ["/robots.txt" robots_handler]]
                article-routes)))
