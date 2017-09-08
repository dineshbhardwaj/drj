(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.json :refer [json-body-request]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            )
  (:import [org.eclipse.jetty.server.handler StatisticsHandler])
  (:gen-class))

(defn handler 
;;     "generating different response depending on ans to 
;;      if you know aricle or not"
  [request]
  (def input_data  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :resolvedQuery)))
;;  (def input_context  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :contexts)))
;;  (if ())
  (response {:speech input_data
             :displayText "Turst me user, It works !!"})
  )

;; latter (defn article-defination []
;; latter   '(#"a.*an.*the" #"a.*the.*an"
;; latter     #"an.*a.*the" #"an.*the.*a"
;; latter     #"the.*an.*a" #"the.*a.*an")
;; latter   )
 

;; matching any of the string in list "find_string_list" in "big_string" 
;; latter (defn match-string [big_string find_string_list] 
;; latter   (loop [ list_len (- (count find_string_list) 1)]
;; latter     (do (def cur_string (nth find_string_list list_len))
;; latter         (println cur_string list_len)
;; latter         (if (.contains big_string cur_string) 
;; latter           true
;; latter           (if (zero? list_len) 
;; latter             false 
;; latter             (recur (dec list_len))))))
;; latter )
;; latter 
;; latter 
;; latter ;; matching any of the regexp in list "find_string_list" in "big_string" 
;; latter (defn match-regexp-string-list [big_string find_string_list] 
;; latter   (loop [ list_len (- (count find_string_list) 1)]
;; latter     (do (def cur_string (nth find_string_list list_len))
;; latter         (println cur_string list_len)
;; latter         (if (re-find cur_string big_string ) 
;; latter           true
;; latter           (if (zero? list_len) 
;; latter             false 
;; latter             (recur (dec list_len))))))
;; latter )

(def app
  (wrap-json-response handler)
  )



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


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
;;    (jetty/run-jetty (site #'app) {:port port :join? false :configurator conf})
    (jetty/run-jetty (site #'app) {:port port :join? false})
))

;; For interactive development:
;; (.stop server)
(defonce server (clojure-getting-started.web/-main))
