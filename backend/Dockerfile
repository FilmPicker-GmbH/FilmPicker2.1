FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /FilmPicker2.1/backend
COPY . .

RUN mvn clean install

CMD mvn spring-boot:run