(ns server.routes
  (:require [blog.article-view :refer [article-page article-page-meta]]
            [blog.articles :refer [articles-list]]
            [blog.view :refer [blog-page]]
            [cv.view :refer [cv-download-page]]
            [hiccup2.core :as h]
            [home.view :refer [home-page]]
            [index.layout :refer [Layout]]
            [pohjavirta.exchange :as exchange]
            [ring.util.response :as resp]))

(defn index_get
  [_req]
  {:status 200,
   :body (str (h/html (h/raw "<!DOCTYPE html>")
                      (Layout {:title "beldmian's blog", :description ""}
                              (home-page))))})
(defn blog_get
  [_req]
  {:status 200,
   :body (str (h/html (h/raw "<!DOCTYPE html>")
                      (Layout {:title "beldmian's blog articles",
                               :description ""}
                              (blog-page))))})

(defn cv_get
  [_req]
  {:status 200,
   :body (str (h/html (h/raw "<!DOCTYPE html>")
                      (Layout {:title "beldmian's cv", :description ""}
                              (cv-download-page))))})

(defn article_get
  [req]
  {:status 200,
   :body (str (h/html (h/raw "<!DOCTYPE html>")
                      (Layout (article-page-meta req) (article-page req))))})

(defn make_article_route
  [[id _article]]
  [(str "/article/" id)
   (exchange/constantly (fn [_] (article_get {:path-params {:id id}})))])

(defn make_articles_router [articles] (map make_article_route articles))

(defn static_handler
  [request]
  (resp/header (resp/resource-response (:uri request))
               "Cache-Control"
               "max-age=31536000"))

(def routes
  [["/" (exchange/constantly index_get)]
   ["/blog" (exchange/constantly blog_get)] ["/cv" (exchange/constantly cv_get)]
   (make_articles_router articles-list) ["/public/*path" static_handler]])
