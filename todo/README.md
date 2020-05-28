# Todo

## Stack
1. React(front)
1. Clojure, MariaDB

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

 미리 컴파일된 jar로 바로 실행가능하다   
 bin/todo.jar를 받아서 실행할 것
