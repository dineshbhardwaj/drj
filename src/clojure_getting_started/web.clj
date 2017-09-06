(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [cheshire.core :as json]
;;            [myapp.web.routes    :as api]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.json :refer [json-response]]
            [ring.middleware.json :refer [json-body-request]]
            [ring.middleware.json :refer [json-params-request]]
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



(defn json_converstion []

  (def resp_data   (response {:speech "input_data" 
                              :data_1 { :data_2 "hello" :data_3 "got"}
                              :displayText "Turst me user, It works !!"}))
  (def json_resp_data  (json-response resp_data  {}))
  (if-let [request (json-params-request json_resp_data {:bigdecimals true} )]
;;request
    (get (json/decode (get-in request [:body])) "data_1")
 )
;; older code   (if-let [request (json-body-request json_resp_data {:keywords? true :bigdecimals true} )]
;; older code     ;;  (def input_data (get-in  (json-body-request (get-in request [:body :originalRequest]) {:keywords? true :bigdecimals true}) [:source]))
;; older code     ;;  (def input_data (get-in  (get-in request [:body :result]) [:resolveQuerry]))
;; older code ;;    (:speech   (json/parse-string (get-in request [:body])))
;; older code ;;   (get-in request [:body])
;; older code  (get (json/decode (get-in request [:body])) "data_1")
;; older code ;;(json/decode  (get-in request [:body]) {:keywords? true :bigdecimals true})
;; older code ;;(json/decode "{\"data_1\":{\"data_2\":\"hello\",\"data_3\":\"got\"},\"displayText\":\"Turst me user, It works !!\",\"speech\":\"input_data\"}") 
;; older code  )
)

  (defn serialize [m sep] (str (clojure.string/join sep (map (fn [[_ v]] _ v) m)) "\n"))

(defn handler 
;;   "generating different response depending on ans to 
;;    if you know aricle or not"
  [request]
;;  (prn request)
;; yes (if-let [request (json-body-request request {:keywords? true :bigdecimals true} )]
;; yes ;;  (def input_data (get-in  (json-body-request (get-in request [:body :originalRequest]) {:keywords? true :bigdecimals true}) [:source]))
;; yes ;;  (def input_data (get-in  (get-in request [:body :result]) [:resolveQuerry]))
;; yes   (def input_data  (get-in request [:body "result" "resolvedQuerry"])))
;; yes ;;  (def input_data (get  (get  (json/decode (get-in request [:body :result])) "result") "resolvedQuerry")) 
;;
;; hold on (def res_wo_json
;; hold on     (response {:speech input_data
;; hold on                :displayText "Turst me user, It works !!"})
;; hold on     )


;; worked wrong (def input_data   (str  (json-body-request (json-response (str (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result]))  {:keywords? true :bigdecimals true}) [:body :resolvedQuerry]  )  ))
;; better (def input_data   (str  (get-in (json-body-request (json-response (str (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])) {}) {:keywords? true :bigdecimals true}) [:body :source]  ))  )
(def input_data   (str   (get-in  (json-body-request (json-response (str (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])) {}) {:keywords? true :bigdecimals true}) [:body]))  )
;;(def input_data   (str (get-in (json/decode (json-body-request (json-response (str (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result]))  {:keywords? true :bigdecimals true}) [:body :resolvedQuerry]  )) :resolvedQuerry) ))
;;(def input_data (serialize (get-in  (json-body-request request {:keywords? true :bigdecimals true}) [:body]) ,)) 
 ;;(def input_data  (get-in request (json-params-request request { :bigdecimals true}) [:params "timestamp"]))


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
