# clojure-web-minsetup


## 실행

```
$ lein run with-profile dev
```

http://127.0.0.1:3000 으로 접속해본다


## 빌드

```
$ lein uberjar
```

target/uberjar/에 backend.jar가 생성된다.
아래와 같이 실행한다.

```
$ java -jar backend.jar
```

환경설정파일은 env/아래에 있는 config.edn이다
빌드 후 jar에 포함되는 환경파일은 env/prd/resources/아래에 있는 파일이 포함된다.

만약 env에 나와있는 환경변수 중 port를 변경할려면 아래와 같이 입력한다.
```
$ java -Dport=9000 -jar backend.jar
```
