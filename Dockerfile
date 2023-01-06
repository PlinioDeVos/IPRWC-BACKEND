FROM openjdk:17-jdk-slim
COPY ./target/sentronics-api.jar sentronics-api.jar

COPY .env .env
RUN . ./.env

ENTRYPOINT /data
ENTRYPOINT ["java", "-jar", "/sentronics-api.jar"]