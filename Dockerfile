FROM openjdk
ADD target/presta-savings.jar presta-savings.jar
ENTRYPOINT ["java", "-jar", "/presta-savings.jar"]