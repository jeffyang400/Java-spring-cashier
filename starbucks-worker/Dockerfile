FROM --platform=linux/amd64 openjdk:11
EXPOSE 8080
ADD ./target/starbucks-worker-0.0.1-SNAPSHOT.jar /srv/starbucks-worker.jar
CMD java -jar /srv/starbucks-worker.jar