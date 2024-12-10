FROM openjdk:17-slim

 # Expose the port that the backend will run on
EXPOSE 8087
# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Download the JAR file from the Nexus repository
RUN curl -o eventsProject-1.0.0.jar -L "http://192.168.137.191:8081/repository/maven-releases/tn/esprit/eventsProject/1.0.0/eventsProject-1.0.0.jar"

# Define the entry point to run the application

ENTRYPOINT ["java", "-jar", "eventsProject-1.0.0.jar"]


