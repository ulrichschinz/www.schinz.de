(ns de.schinz.components.home
  (:require [de.schinz.snippets.core :as sn]
            [de.schinz.helpers.core :as h]
            [clojure.java.io :as io]
            [de.schinz.mdbackend.fs :as fs]
            [markdown.core :as md]))


(defn home [req]
  (let [p (io/resource "index.md")]
    (sn/embed (h/get-context (:uri req)) (md/md-to-html-string (slurp p)))))
(home {:uri "/"})
