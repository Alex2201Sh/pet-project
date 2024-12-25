#FROM openjdk:17-alpine
#COPY .mvn .mvn
#COPY mvnw .
#COPY pom.xml .
#RUN ./mvnw clean package

#FROM openjdk:17-alpine
#COPY .mvn .mvn
#COPY mvnw .
#COPY pom.xml .
#RUN ./mvnw clean package
#ARG JAR_FILE=target/autodealer-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
#COPY ${JAR_FILE} app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]

#------------------------------------
#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#COPY .mvn/ .mvn
#COPY ./src /app/src
#COPY ./pom.xml /app
#RUN mvn clean package  # Сборка вашего приложения
#CMD ["java", "-jar", "target/*.jar"]
#----------------------------------
#FROM eclipse-temurin:17-jdk-alpine as builder
#WORKDIR /opt/app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install
#
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /opt/app
#COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
##EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
#----------------------------------
FROM maven:3.8.5-openjdk-17 AS build
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]