(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [camel-snake-kebab.core :as kebab]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello from Heroku"})


(defn handler 
;;   "generating different response depending on ans to 
;;    if you know aricle or not"
  [request]
  (def input_data (get-in request [:resolvedQuery "yes, I have read the article"]))
  (response {:speech input_data
             :displayText "Turst me user, It works !!"}))
;;  (response "Uploaded user.")


(def app
;;  (wrap-json-body handler {:keywords? true :bigdecimals true})
  (wrap-json-response handler)
  )

;;(defn handler [request]
;;;;  (response {:displayText "Bar"}))
;;  (response {:speech "Turst me Deepak, It works !!"
;;             :displayText "Turst me Deepak, It works !!"}))


;; working code for jason response (defn handler [request]
;; working code for jason response ;;  (response {:displayText "Bar"}))
;; working code for jason response   (process request)
;; working code for jason response   (response {:speech "Turst me Deepak, It works !!"
;; working code for jason response 
;; working code for jason response (def app
;; working code for jason response   (wrap-json-response handler))


;;(load-file "/Users/Apple/clojure-getting-started/src/clojure_getting_started/response.clj")

;;(defn handler [request]
;;;;  (response {:displayText "Bar"}))
;;  (response {:speech "Turst me Deepak, It works !!"
;;             :displayText "Turst me Deepak, It works !!"}))
;;
;;(def app
;;  (wrap-json-response handler))
;;
;; addition from example 2nd 
;;(defroutes app
;;  (GET "/camel" {{input :input} :params}
;;       {:status 200
;;        :headers {"Content-Type" "text/plain"}
;;        :body (kebab/->CamelCase input)})
;;  (GET "/snake" {{input :input} :params}
;;       {:status 200
;;        :headers {"Content-Type" "text/plain"}
;;        :body (kebab/->snake_case input)})
;;  (GET "/kebab" {{input :input} :params}
;;       {:status 200
;;        :headers {"Content-Type" "text/plain"}
;;        :body (kebab/->kebab-case input)})
;;  (GET "/" []
;;       (splash))
;;  (ANY "*" []
;;       (route/not-found (slurp (io/resource "404.html")))))

;; original example
;;(defroutes app
;;  (GET "/" []
;;       (splash))
;;  (ANY "*" []
;;       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
