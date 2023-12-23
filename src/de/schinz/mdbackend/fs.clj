(ns de.schinz.mdbackend.fs
  (:require [clojure.string :as st]
            [clojure.java.io :as io]))

(defn get-dirs [basedir]
  (map
    #(str %)
    (filter
      #(not (re-matches #"(.*)\.(.*)" (str %)))
      (filter
        #(.isDirectory %) (file-seq basedir)))))

(defn strip-basedir [base dirs]
  (let [sc (count (st/split (str base) #"/"))]
    (map #(subvec % sc) (filterv #(> (count %) sc) dirs))))

(defn build-dir-struc [basedir]
  (let [dirs (get-dirs)
        split-d (map #(st/split % #"/") dirs)]
    (reduce #(assoc-in %1 (map keyword %2) {}) {} (strip-basedir basedir split-d))))

(defn ext [f]
  (last (st/split f #"\.")))

(defn no-other-dots? [n]
  (= 1 (count (rest (reverse (st/split n #"\."))))))

(defn name-sani [n]
  (let [fname (.getName n)]
    (and
      (= "md" (ext fname))
      (no-other-dots? fname))))

(defn sanitize [files]
  (let [f (filter #(.isFile %) files)]
    (filter #(name-sani %) f)))

(defn list-mds
  ([basedir] (list-mds basedir nil))
  ([basedir dir]
   (let [bd (io/file (str basedir "/" dir))
         sanitized (sanitize (.listFiles bd))]
     (reduce #(assoc %1 (keyword (first (st/split (.getName %2) #"\."))) %2) {} sanitized))))

(defn build-file [dir sheet]
  (if (= "/" (str dir))
    (io/resource sheet)
    (io/file (str dir "/" sheet ".md"))))

(defn valid? [s-file]
  (and
    (no-other-dots? (str s-file))
    (.exists s-file)
    (.isFile s-file)))

(defn load-sheet! [basedir sheet]
  (let [sf (build-file basedir sheet)]
    (if (valid? sf)
      (slurp sf)
      (str "There is no such sheet. Cannot show " sheet "!"))))
