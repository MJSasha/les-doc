FROM openjdk:18
EXPOSE 8080
ADD target/statistics-service.jar statistics-service.jar
ENTRYPOINT ["java", "-jar", "/statistics-service.jar"]