FROM eclipse-temurin:25-jre
WORKDIR /app
ARG JAR_FILE=build/libs/portfolio-*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]