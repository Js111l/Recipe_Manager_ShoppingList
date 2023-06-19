FROM openjdk:17
ADD ./build/libs/sh app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
