(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
;;            [myapp.web.routes    :as api]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.json :refer [json-response]]
            [ring.middleware.json :refer [json-body-request]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [camel-snake-kebab.core :as kebab])
  (:import [org.eclipse.jetty.server.handler StatisticsHandler])
  (:gen-class))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello from Heroku"})



(defn conf
  [server]
  (let [stats-handler (StatisticsHandler.)
        default-handler (.getHandler server)]
    (.setHandler stats-handler default-handler)
    (.setHandler server stats-handler)
;;    (.setStopTimeout  server a-minute)
    (.setStopAtShutdown server true)))


(defn handler 
;;   "generating different response depending on ans to 
;;    if you know aricle or not"
  [request]
;;  (prn request)
(if-let [request (json-body-request request {:keywords? true :bigdecimals true} )]
  (def input_data (get-in request [:body :result :resolveQuerry]))
;;  (def input_data (get-in  (get-in request [:body :result]) [:resolveQuerry]))
;;  (def input_data (get-in request [:body :timestamp]))
    )
;;
;; hold on (def res_wo_json
;; hold on     (response {:speech input_data
;; hold on                :displayText "Turst me user, It works !!"})
;; hold on     )
    (response {:speech input_data
               :displayText "Turst me user, It works !!"})

  ;; latter  (response {:speech input_data
  ;; latter             :displayText "Turst me user, It works !!"})
  ;;  (json-response res_wo_json)
    )

;;  (response "Uploaded user.")


(def app
;;  (wrap-json-body handler {:keywords? true :bigdecimals true})
  (wrap-json-response handler)
                      )

;;(defn handler [request]
;;;;  (response {:displayText "Bar"}))
;;  (response {:speech "Turst me Deepak, It works !!"
;;             :displayText "Turst me Deepak, It works !!"}))



;;(load-file "/Users/Apple/clojure-getting-started/src/clojure_getting_started/response.clj")

;; response back working  (defn handler [request]
;; response back working  ;;  (response {:displayText "Bar"}))
;; response back working    (response {:speech "Turst me Deepak, It works !!"
;; response back working               :displayText "Turst me Deepak, It works !!"}))
;; response back working  
;; response back working  (def app
;; response back working    (wrap-json-response handler))

;; addition from example 2nd 
;;second example (defroutes app
;;second example   (GET "/camel" {{input :input} :params}
;;second example        {:status 200
;;second example         :headers {"Content-Type" "text/plain"}
;;second example         :body (kebab/->CamelCase input)})
;;second example   (GET "/snake" {{input :input} :params}
;;second example        {:status 200
;;second example         :headers {"Content-Type" "text/plain"}
;;second example         :body (kebab/->snake_case input)})
;;second example   (GET "/kebab" {{input :input} :params}
;;second example        {:status 200
;;second example         :headers {"Content-Type" "text/plain"}
;;second example         :body (kebab/->kebab-case input)})
;;second example   (GET "/" []
;;second example        (splash))
;;second example   (ANY "*" []
;;second example        (route/not-found (slurp (io/resource "404.html")))))

;; original example
;; first example (defroutes app
;; first example   (GET "/" []
;; first example        (splash))
;; first example   (ANY "*" []
;; first example        (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false :configurator conf})))

;; For interactive development:
;; (.stop server)
(defonce server (clojure-getting-started.web/-main))
