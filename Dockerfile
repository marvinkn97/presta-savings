FROM openjdk:latest
COPY target/presta-savings-0.0.1-SNAPSHOT.jar presta-savings-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "presta-savings.jar"]