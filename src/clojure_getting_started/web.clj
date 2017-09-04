(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.json :refer [json-response]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [camel-snake-kebab.core :as kebab]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello from Heroku"})


;; not working(defn handler 
;; not working;;   "generating different response depending on ans to 
;; not working;;    if you know aricle or not"
;; not working  [request]
;; not working;;  (prn request)
;; not working;;  (def input_data (get-in request [:body "resolveQuerry"]))
;; not working  (response {:speech "hello"
;; not working             :displayText "Turst me user, It works !!"})
;; not working
;; not working;; latter  (response {:speech input_data
;; not working;; latter             :displayText "Turst me user, It works !!"})
;; not working  ;;  (json-response response)
;; not working  )
;; not working
;; not working;;  (response "Uploaded user.")
;; not working
;; not working
;; not working(def app
;; not working;;  (wrap-json-body handler {:keywords? true :bigdecimals true})
;; not working  (wrap-json-response handler)
;; not working                      )

;;(defn handler [request]
;;;;  (response {:displayText "Bar"}))
;;  (response {:speech "Turst me Deepak, It works !!"
;;             :displayText "Turst me Deepak, It works !!"}))



;;(load-file "/Users/Apple/clojure-getting-started/src/clojure_getting_started/response.clj")

;; was working (defn handler [request]
;; was working ;;  (response {:displayText "Bar"}))
;; was working   (response {:speech "Turst me Deepak, It works !!"
;; was working              :displayText "Turst me Deepak, It works !!"}))
;; was working 
;; was working (def app
;; was working   (wrap-json-response handler))
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
(defroutes app
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
