(ns todo.routes
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [clojure.string :refer [blank?]]
            [compojure.core :refer [defroutes context GET POST PUT DELETE PATCH]]
            [cheshire.core :refer [parse-string generate-string]]
            [ring.util.http-response :refer [created ok not-found bad-request found set-cookie]]
            [todo.config :refer [env]]
            [todo.layout :refer [render]]
            [todo.db :refer [*db*]]
            [slingshot.slingshot :refer [throw+]]
            [clojure.java.jdbc :as jdbc]))


(defn checking-error [{:keys [subject content] :as todo}]
  (log/debug "Todo =>" todo)
  (let [err (cond-> {}
              (blank? subject) (assoc :subject "제목이 없습니다")
              (blank? content) (assoc :content "내용이 없습니다"))]

    (if (empty? err)
      false
      (throw+ {:type :validation
               :message err}))))


(defn insert-todo [todo]
  (if-not (checking-error todo) ;에러가 없으면 db 에 insert작업을 한다
    (->> (jdbc/insert! *db* :todos todo)
         first
         :insert_id
         (assoc todo :id))))



(defn delete-todo [id]
  "삭제 된 레코드 수를 돌려준다"
  (-> (jdbc/delete! *db* :todos ["id =?" id])
      first))


(defn update-todo [{:keys [id] :as todo}]
  "update 실행 후 영향을 받은 레코드 수를 돌려준다"
  (if-not (checking-error todo)
    (-> (jdbc/update! *db* :todos (dissoc todo :id) ["id =?" id])
        first)))


(defn select-todo
  ([id]
   (first
    (jdbc/query *db* ["select * from todos where id =?" id])))

  ([]
   (jdbc/query *db* ["select * from todos"])))


(defroutes html-routes
  (GET "/ping" []
    (ok {:status "pong"}))

  (GET "/*" []
    (render "index.html")))


(defroutes todo-api

  (GET "/api/todos" []
    (ok
     (select-todo)))

  (GET "/api/todos/:id" [id]
    (if-let [todo (select-todo id)]
      (ok todo)
      (not-found)))


  (POST "/api/todos" {{:keys [subject content]} :params}
    (ok
     (insert-todo {:subject subject
                   :content content})))


  (PUT "/api/todos/:id" [todo]
    (ok
     {:affected (first (update-todo todo))}))


  (DELETE "/api/todos/:id" [id]
    (let [count (delete-todo id)]
      (if (zero? count)
        (not-found)
        (ok
         {:affected count}))))
  )
