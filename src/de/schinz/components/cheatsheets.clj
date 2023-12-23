(ns de.schinz.components.cheatsheets
  (:require [markdown.core :as md]
            [de.schinz.snippets.core :as sn]
            [clojure.java.io :as io]
            [de.schinz.helpers.core :as h]
            [de.schinz.mdbackend.fs :as fs]))

;(def cheatsheet-basedir (io/file "/home/uli/projects/sit/cheatsheets/"))
(def cheatsheet-basedir (io/file (System/getenv "WWW_CS_DIR")))

(defn md-links []
  (loop  [mds (seq (fs/list-mds cheatsheet-basedir))
          links ""]
    (if (empty? mds)
      links
      (recur (rest mds) (str links (str "<a href=\"/cheatsheets/" (name (first (first mds))) "\">"
                                        (name (first (first mds)))
                                        "</a><br />"))))))
(defn overview [req]
  (let [mdl (md-links)]
    (sn/embed (h/get-context (:uri req)) mdl)))

(defn show-sheet [sheet req]
  (let [sheet (fs/load-sheet! cheatsheet-basedir sheet)]
    (sn/embed (h/get-context (:uri req)) (md/md-to-html-string sheet))))
