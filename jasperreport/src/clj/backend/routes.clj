(ns backend.routes
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [compojure.core :refer [defroutes context GET POST PUT DELETE PATCH]]
            [cheshire.core :refer [parse-string generate-string]]
            [ring.util.http-response :refer [created ok not-found bad-request found set-cookie]]
            [backend.config :refer [env]]
            [backend.layout :refer [render]])

  (:import [net.sf.jasperreports.engine JasperCompileManager JasperFillManager JasperExportManager JREmptyDataSource]
           [net.sf.jasperreports.engine.data JRMapCollectionDataSource]))



(def compiled-jrxml
  (-> (io/resource "CMS_2020_0514_A4.jrxml")
      (io/input-stream)
      (JasperCompileManager/compileReport)
      delay))

(defn now []
  (-> (java.text.SimpleDateFormat. "yyyy년 MM월 dd일")
      (.format (java.util.Date.))))


(defn make-pdf [datas]
  ;배열 데이터를 넘긴다
  (-> (JasperFillManager/fillReport
       @compiled-jrxml
       (java.util.HashMap.)
       (JRMapCollectionDataSource.
        (map (fn [{:keys [username address department sn position period job purpose date coname coaddr ceo]
                   :or   {purpose "제출용" date (now) coname "(주)_____" coaddr "서울특별시 __구 ________ 전산센터" ceo "사장님"}}]
               (java.util.HashMap.
                {"username" username
                 "address" address
                 "department" department
                 "sn" sn
                 "position" position
                 "period" period
                 "job" job
                 "purpose" purpose
                 "issueDate" date
                 "companyName" coname
                 "companyAddress" coaddr
                 "nameOfCEO" ceo})) datas )))
      (JasperExportManager/exportReportToPdf)))



(defroutes html-routes
  (GET "/ping" []
    (ok {:status "pong"}))

  (GET "/" []
    (render "jasper.html"))

  (POST "/api/employee-serve" [datas]
    {:status 200
     :headers {"Content-Type" "application/pdf"}
     :body (java.io.ByteArrayInputStream. (make-pdf (parse-string datas true)))}))
