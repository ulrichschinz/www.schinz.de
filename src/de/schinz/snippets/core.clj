(ns de.schinz.snippets.core
  (:require [clojure.java.io :as io]))

;(def header (slurp (io/resource "public/assets/html/header.html")))
;(def footer (slurp (io/resource "public/assets/html/footer.html")))

(defn navigation [highlight]
  (let [highlightmap (assoc {:home ""
                             :cheatsheets ""} highlight "active\" aria-current=\"page")]
    (str
    "<ul class=\"nav nav-pills\">
      <li class=\"nav-item\"><a href=\"/\" class=\"nav-link " (:home highlightmap) "\">Home</a></li>
      <li class=\"nav-item\"><a href=\"/cheatsheets\" class=\"nav-link " (:cheatsheets highlightmap) "\">Cheatsheets</a></li>
    </ul>" )))

(defn header [context]
  (let [ho (slurp (io/resource "public/assets/html/header_open.html"))
        hc (slurp (io/resource "public/assets/html/header_close.html"))]
    (str ho (navigation context) hc)))

(defn footer []
  (slurp (io/resource "public/assets/html/footer.html")))

(defn home_body []
  (slurp (io/resource "public/assets/html/home_body.html")))

(defn embed [context body]
  (str (header context) body (footer)))
