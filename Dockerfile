FROM openjdk:17
EXPOSE 80
ADD target/gateway.jar gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]