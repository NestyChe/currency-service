FROM openjdk:11
EXPOSE 8080
RUN mkdir ./app
COPY ./currency-service-0.0.1-SNAPSHOT.jar ./app
ENTRYPOINT ["java","-jar","./app/currency-service-0.0.1-SNAPSHOT.jar"]