(ns server.rss
  (:require [clj-rss.core :as rss]))

(defn make-rss-item
  [[id article]]
  {:title (:title article),
   :description (:description article),
   :link (str "https://blog.beldmian.ru/article/" id)})

(defn make-rss-feed
  [articles]
  (rss/channel-xml {:title "beldmian's blog",
                    :link "http://blog.beldmian.ru/rss",
                    :description "beldmian's personal blog"}
                   (map make-rss-item articles)))

(defn make-rss-feed-handler
  [articles]
  (fn [_]
    {:body (make-rss-feed articles),
     :status 200,
     :headers {"Content-Type" "application/rss+xml"}}))
