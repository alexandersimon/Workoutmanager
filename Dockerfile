FROM eclipse-temurin:21-jdk-alpine

COPY build/libs/Workoutmanager-1.0-SNAPSHOT.jar /workoutmanager.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/workoutmanager.jar"]