FROM amazoncorretto:21.0.6

WORKDIR /app

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=ewm-service/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]