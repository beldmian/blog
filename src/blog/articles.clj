(ns blog.articles
  (:require [macro.core :as mc]))

(defrecord Article [title description date contents])

(def articles-list
  {"yastation"
     (Article.
       "Creating yastation - a console client for managing Yandex Station"
         "One day, while sitting at work and listening to music on my Yandex Station, I discovered something interesting - I couldn't simply skip the track. You might ask, \"How could that happen?\" I'd reply that yesterday I lost my voice during a heated political debate, and my phone, inconveniently, was further away than I could reach. Ultimately, this unfortunate situation prompted me to create my own solution"
       "03/27/2022" (mc/inline-resource "articles/yastation.md"))})
