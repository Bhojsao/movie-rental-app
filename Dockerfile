FROM openjdk:17
EXPOSE 9082
RUN mkdir -p /exec

COPY target/movie-rental-app-0.0.1-SNAPSHOT.jar /exec/movie-rental-app-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/exec/movie-rental-app-0.0.1-SNAPSHOT.jar"]