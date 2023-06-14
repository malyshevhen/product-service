FROM openjdk:17-alpine
ADD target/*.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]