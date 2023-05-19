FROM openjdk:17-alpine
WORKDIR /app
COPY . .
RUN ./build.sh
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/target/social_media-0.0.1-SNAPSHOT.jar"]