# **<p align="center">LesDoc</p>**

## **About the project**

Pet project, the main purpose of which is to work with files and external APIs. Unit testing should also be actively used in the project.

The project consists of two parts: **Frontend** and **Backend**. 

**Backend** is a `Java` project in the `Spring` framework

**Frontend** is a `React` application with `Typescript` compiled by `Webpack`

## **Project Stack**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
![Webpack](https://img.shields.io/badge/webpack-%238DD6F9.svg?style=for-the-badge&logo=webpack&logoColor=black)
![Bootstrap](https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

----------

## **<p align="center">Links, commands, etc.</p>**

### **Launching API**

1. Launch [docker-compose](https://github.com/MJSasha/les-doc/blob/master/docker-compose.yml)
``` bash
docker-compose up
```
1. Go to the [backend](https://github.com/MJSasha/les-doc/tree/master/backend) folder and open the console in it 
2. Launch orchestrator:
``` bash
.\mvnw clean install spring-boot:run -pl orchestrator
```
1. Open another console and run lessons-files-service:
``` bash
.\mvnw clean install spring-boot:run -pl lessons-files-service
```
1. *To collect statistics*, open another console and run statistics-service
``` bash
.\mvnw clean install spring-boot:run -pl statistics-service
```

Orchestrator is deployed on port 8080, while SwaggerUI is available via the link [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html).

### **Launching React App**

``` bash
cd frontend
yarn install
yarn start
```
