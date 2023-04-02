FROM openjdk:11-jre-slim
COPY target/*.jar /app/app.jar
COPY src/main/resources/application.yml /app/application.yml
WORKDIR /app
CMD ["java", "-jar", "/app/app.jar", "-Duser.timezone=Asia/Shanghai", "--spring.config.location=/app/application.yml"]
EXPOSE 8086
