(ns async-tea-party.core
  (:require [clojure.core.async :as async])
  (:gen-class))



;; (def tea-channel (async/chan 10))

;; (async/>!! tea-channel :cup-of-tea1)
;; (async/>!! tea-channel :cup-of-tea2)
;; (async/>!! tea-channel :cup-of-tea3)
;; (async/>!! tea-channel :cup-of-tea4)
;; (async/>!! tea-channel :cup-of-tea5)

;; (async/close! tea-channel)

;; (async/>!! tea-channel :cup-of-tea6)

;; (async/<!! tea-channel)
;; (async/<!! tea-channel)
;; (async/<!! tea-channel)

;; (let [tea-channel (async/chan)]
;;   (async/go (async/>! tea-channel :cup-of-tea-1))
;;   (async/go (println "Thanks for " (async/<! tea-channel))))



;; (def tea-channel (async/chan))

;; (async/go-loop []
;;   (println "Thanks for" (async/<! tea-channel))
;;   (recur))

;; ;; (async/go
;; ;;   (while true (println "Thanks!!! " (async/<! tea-channel))))

;; (async/>!! tea-channel :hot-cup-of-tea)



;; (def tea-channel (async/chan 10))
;; (def milk-channel (async/chan 10))
;; (def sugar-channel (async/chan 10))

;; (async/go-loop []
;;   (let [[v ch] (async/alts! [tea-channel
;;                              milk-channel
;;                              sugar-channel])]
;;     (println "Got " v " from " ch)
;;     (recur)))

;; (async/>!! sugar-channel :milk)


(def google-tea-service-chan (async/chan 10))
(def yahoo-tea-service-chan (async/chan 10))

(defn random-add []
  (reduce + (repeat (rand-int 100000) 1)))


(defn request-google-tea-sevice []
  (async/go
    (random-add)
    (async/>! google-tea-service-chan
             "tea compliments of google")))

(defn request-yahoo-tea-sevice []
  (async/go
    (random-add)
    (async/>! yahoo-tea-service-chan
             "tea compliments of yahoo!")))

(def result-chan (async/chan 10))

(defn request-tea []
  (request-google-tea-sevice)
  (request-yahoo-tea-sevice)
  (async/go
    (let [[v]
          (async/alts!
           [google-tea-service-chan yahoo-tea-service-chan])]
         (async/>! result-chan v))))




(defn -main []
  "Requesting tea..."
  (request-tea)
  (println (async/<!! result-chan)))











