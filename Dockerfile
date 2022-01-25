FROM openjdk:11
WORKDIR /opts/hello-spring
COPY build/libs/demo-0.0.1-SNAPSHOT.jar ./
CMD java -jar demo-0.0.1-SNAPSHOT.jar