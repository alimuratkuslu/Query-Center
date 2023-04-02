FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/querycenter-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} querycenter.jar
ENTRYPOINT ["java","-jar","querycenter.jar"]