FROM openjdk:11.0.11-jdk as build
COPY . /build
WORKDIR /build
RUN ["chmod", "+x", "./mvnw"]
RUN ["./mvnw","install"]

FROM openjdk:11.0.11-jre
EXPOSE 8080
RUN mkdir /app
COPY --from=build /build/target/*task*.jar /app/task.jar
COPY ./flights.csv /app/flights.csv
ENV FLIGHTS_SOURCE_FILE_PATH=/app/flights.csv
ENTRYPOINT ["java","-jar","/app/task.jar"]