(defn handler [request]
;;  (response {:displayText "Bar"}))
  (response {:speech "Turst me Deepak, It works !!"
             :displayText "Turst me Deepak, It works !!"}))

(def app
  (wrap-json-response handler))
