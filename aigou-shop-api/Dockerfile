FROM library/openjdk:17
VOLUME /tmp
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
COPY /target/aigou-shop-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
