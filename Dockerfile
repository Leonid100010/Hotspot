FROM openjdk:11-jre-slim
COPY target/*.jar /app/app.jar
COPY src/main/resources/application.yml /app/application.yml
COPY src/main/resources/logback-spring.xml /app/logback-spring.xml
WORKDIR /app
CMD ["java", "-jar", "/app/app.jar", "-Duser.timezone=Asia/Shanghai", "--spring.config.location=/app/application.yml"]
EXPOSE 8086
