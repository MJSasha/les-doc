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

![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

----------

## **<p align="center">Links, commands, etc.</p>**

### **Launching API**

#### **Launching by Docker**

The API is launched using Docker. The API image is uploaded to [DockerHub]([https://](https://hub.docker.com/repository/docker/mjsasha/backend/general)). The project can be started from the [docker-compose]([https://](https://github.com/MJSasha/les-doc/blob/master/docker-compose.yml)) file, which is located in the root of the project. Launch Command:

``` bash
docker-compose up
```
For update use

``` bash
docker-compose up -d
```

The API is deployed on port 8080, while SwaggerUI is available via the link [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html).

#### **Launching by maven wrapper**

If you don't want to use Docker, then you can run the backend with the following command from the folder [backend](https://github.com/MJSasha/les-doc/tree/master/backend):

``` bash
.\mvnw clean install spring-boot:run -Dspring-boot.run.profiles=dev
```

In this case, the In memory database will be started. If you want to use a regular database, then use the command:

``` bash
.\mvnw clean install spring-boot:run
```

### **Launching React App**

``` bash
cd frontend
yarn install
yarn start
```
