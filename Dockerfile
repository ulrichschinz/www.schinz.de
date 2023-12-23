FROM debian:trixie-slim
#FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get -y upgrade && apt-get -y install bash curl openjdk-21-jre && rm -rf /var/lib/apt/lists/*
RUN curl -o /tmp/linux-install-1.11.1.1252.sh https://download.clojure.org/install/linux-install-1.11.1.1252.sh && chmod +x /tmp/linux-install-1.11.1.1252.sh && /tmp/linux-install-1.11.1.1252.sh && rm /tmp/linux-install-1.11.1.1252.sh

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY target/www-0.1.0-SNAPSHOT.jar /opt/app/www.jar

EXPOSE 3030

CMD ["java", "-jar", "www.jar"]
