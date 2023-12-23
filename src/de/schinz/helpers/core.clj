(ns de.schinz.helpers.core
  (:require [clojure.string :as st]))

(defn get-context [uri]
  (let [us (st/split uri #"/")]
    (if (> (count us) 0)
      (keyword (nth us 1))
      :home)))
