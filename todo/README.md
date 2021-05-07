# Todo

## Stack
1. React(front)
1. Clojure, MariaDB

# Mariadb 

```
docker run --name mariadb -p3306:3306 -e TZ=Asia/Seoul -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=todos -d mariadb:10 ----character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

#  Table Setup

 todos라는 테이블을 작성합니다.

```sql
create table todos ( 
  id int not null auto_increment primary key,
  subject varchar(100) not null, 
  content text not null, 
  done tinyint(1) default 0,
  created_at timestamp default current_timestamp
);
```

# Backend 구동

```
$ cd backend
$  lein with-profile dev run
```

# Front 구동

```
$ cd front
$ yarn start
```

# binary

성급한 개발자에게 권장합니다.  
미리 컴파일된 jar로 바로 실행가능  
bin/todo.jar를 받아서 실행할 것
