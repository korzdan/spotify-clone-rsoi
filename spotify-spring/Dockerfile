FROM amazoncorretto:19
COPY /src/main/resources/db/migration /src/main/resources/db/migration
ADD /build/libs/spotify-spring-0.0.1-SNAPSHOT.jar spotify-backend.jar
ENTRYPOINT ["java", "-jar", "spotify-backend.jar"]
