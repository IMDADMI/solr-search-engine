FROM openjdk:19-jdk-alpine
WORKDIR     /solrApp
COPY       target/*.jar ./
ENV port=8080
EXPOSE ${port}
ENTRYPOINT  ["java", "-jar","Solr-SEs-0.0.1-SNAPSHOT.jar"]
