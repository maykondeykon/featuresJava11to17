FROM openjdk:17
COPY ./out/production/features-java-11-to-17 /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "Feature"]