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
;;  (def input_data  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :resolvedQuery)))
;;  (def input_context  (str   (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  ))
;;  (def input_context  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :contexts)))
  (def input_context  (str   (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :contexts)))
  (if (.contains "hello" input_context)
    (do  
      (def input_data  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :resolvedQuery)))
      (def aritcle_deination_regexp (article-defination))
      (if  (match-regexp-string-list input_data aritcle_deination_regexp)
        (def input_data "your Article defination looks ok. We declared Article as a or an and the.")
        (def input_data "your Article defination does not seems to be correct. Article as a or an and the.")
      )
    )
    (def input_data (str "your context did not match expected. Input context is : " input_context ))
  )
;; latter orignal code  (if (.contains "{:name \"got_article_defination\", :parameters {}, :lifespan 5}" input_context)
;; latter orignal code    (do  
;; latter orignal code      (def input_data  (str  (get  (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result])  :resolvedQuery)))
;; latter orignal code      (def aritcle_deination_regexp (article-defination))
;; latter orignal code      (if  (match-regexp-string-list input_data aritcle_deination_regexp)
;; latter orignal code        (def input_data "your Article defination looks ok. We declared Article as a or an and the.")
;; latter orignal code        (def input_data "your Article defination does not seems to be correct. Article as a or an and the.")
;; latter orignal code      )
;; latter orignal code    )
;; latter orignal code    (def input_data (str "your context did not match expected. Input context is : " input_context ))
;; latter orignal code  )
  (def hello "hello")
  (response {:speech input_data
             :displayText "Turst me user, It works !!"})
  )

(defn article-defination []
  '(#"a.*an.*the" #"a.*the.*an"
    #"an.*a.*the" #"an.*the.*a"
    #"the.*an.*a" #"the.*a.*an")
  )
 

;; matching any of the string in list "find_string_list" in "big_string" 
 (defn match-string [big_string find_string_list] 
   (loop [ list_len (- (count find_string_list) 1)]
     (do (def cur_string (nth find_string_list list_len))
         (println cur_string list_len)
         (if (.contains big_string cur_string) 
           true
           (if (zero? list_len) 
             false 
             (recur (dec list_len))))))
 )
 
 
 ;; matching any of the regexp in list "find_string_list" in "big_string" 
 (defn match-regexp-string-list [big_string find_string_list] 
   (loop [ list_len (- (count find_string_list) 1)]
     (do (def cur_string (nth find_string_list list_len))
         (println cur_string list_len)
         (if (re-find cur_string big_string ) 
           true
           (if (zero? list_len) 
             false 
             (recur (dec list_len))))))
 )

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
    (jetty/run-jetty (site #'app) {:port port :join? false})
))

;; For interactive development:
;; (.stop server)
(defonce server (clojure-getting-started.web/-main))
