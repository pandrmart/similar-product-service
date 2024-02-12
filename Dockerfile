FROM openjdk:12
VOLUME /tmp
EXPOSE 5000
ADD ./target/springboot-similar-product-service-0.0.1-SNAPSHOT.jar similar-product-service.jar
ENTRYPOINT ["java", "-jar", "/similar-product-service.jar"]
