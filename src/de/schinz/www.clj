(ns de.schinz.www
  (:require [org.httpkit.server :as server]
            [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [de.schinz.components.home :as home]
            [de.schinz.components.cheatsheets :as cs])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] home/home)
  (context "/cheatsheets" []
           (GET "/" [] cs/overview)
           (GET "/:id" [id :as r] (cs/show-sheet id r)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes site-defaults))

(defn -main
  "-main starts the server with given ip and port"
  [& {:keys [ip port]
      :or {ip "0.0.0.0"
           port 3030}}]
  (println (str "INFO: Starting http-kit server on: " ip ":" port))
  (server/run-server app {:port port}))
