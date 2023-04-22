FROM openjdk:17 AS build
EXPOSE 8080

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests=true

FROM openjdk:17
WORKDIR querycenter
COPY --from=build target/*.jar querycenter.jar
ENTRYPOINT ["java","-jar","querycenter.jar"]