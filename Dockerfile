FROM openjdk:11 AS base
WORKDIR /opts/hello-spring
COPY ./ ./
RUN ./gradlew assemble

FROM openjdk:11
WORKDIR /opts/hello-spring
COPY --from=base /opts/hello-spring/build/libs/demo-0.0.1-SNAPSHOT.jar ./
CMD java -jar demo-0.0.1-SNAPSHOT.jar