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

;; matching any of the string in list "find_string_list" in "big_string" 
;; note required (defn match-string [big_string find_string_list] 
;; note required   (loop [ list_len (- (count find_string_list) 1)]
;; note required     (do (def cur_string (nth find_string_list list_len))
;; note required         (println cur_string list_len)
;; note required         (if (.contains big_string cur_string) 
;; note required           true
;; note required           (if (zero? list_len) 
;; note required             false 
;; note required             (recur (dec list_len))))))
;; note required )
 
 
(defn article-def
 []
 (list #"a.*an.*the" #"a.*the.*an"  #"an.*a.*the" #"an.*the.*a"  #"the.*an.*a" #"the.*a.*an")
 )

(defn article-defnation
 []
 [ "Article is a or an and the." "Examples are : "  "there is a cat in the room" "there is an elephant in the zoo" "I go to the gym everyday." ]
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

(defn get_output_data 
  [input_context input_data]
  (def output_data input_data)
  (cond
   (.contains input_data "play")
   (do
     (if (re-find  #"[tT]era.*[sS]ong" input_data) 
       (def output_data "<speak> <audio src=\"https://drj1.000webhostapp.com/tera.mp3\"> didn't get your MP3 audio file </audio> </speak>")
       (if (re-find   #"[tT]ujhse [Nn]ara[jz].*[sS]ong" input_data)  
         (def output_data "<speak> <audio src=\"https://drj1.000webhostapp.com/tujhse_naraaz_nahin_zindagi__male__-_masoom_songs_-__naseeruddin_shah_-_jugal_hansraj__-_filmigaane.mp3\"> didn't get your MP3 audio file </audio> </speak>")
         (if (re-find   #"[Bb][ei]+n[a]+.*[sS]ong" input_data)  
           (def output_data "<speak> <audio src=\"https://drj1.000webhostapp.com/bina.mp3\"> didn't get your MP3 audio file </audio> </speak>")
           (if (re-find #"[Kk]yon[ ]*[kK][ei]+ [sS]ong" input_data)  
             (def output_data "<speak> <audio src=\"https://drj1.000webhostapp.com/kyonki.mp3\"> didn't get your MP3 audio file </audio> </speak>")))))))
;; older for context  (cond
;; older for context   (.contains input_context "{:name \"got_article_defination\", :parameters {}, :lifespan 5}")
;; older for context   (do  
;; older for context     (def article_regexp   (article-def))
;; older for context     (if  (match-regexp-string-list input_data article_regexp)
;; older for context       (def output_data "your Article defination looks ok. Article is a or an and the.")
;; older for context       (def output_data (str  "your Article defination does not seems to be correct. input: "  input_data " regexp : " article_regexp ))))
;; older for context   :else (def output_data (str "your context did not match expected. Input context is : " input_context )))
output_data
)

(defn handler 
;;     "generating different response depending on ans to 
;;      if you know aricle or not"
  [request]
  (print "hello")
;;  (def map_result (get-in (json-body-request request {:keywords? true :bigdecimals true}) [:body :result]))
;;  (def map_result_str (str map_result))
;;  (def input_context  (str   (get map_result   :contexts)))
;;  (def input_data  (str  (get  map_result  :resolvedQuery)))
  (response {:success  "File successfully uploaded"
             :pass     "hello"}))
;; changing for android  (response {:speech  (get_output_data input_context input_data) 
;; changing for android             :displayText "turst me it works"}))
;;             :displayText "Turst me user, It works !!"}))
;;  (response {:speech (get_output_data input_context input_data) 
;;             :displayText "Turst me user, It works !!"}))

 


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
