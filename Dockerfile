# remove comments of code that are not relevant!


######################################################



# # Use an official OpenJDK runtime as a parent image
# FROM openjdk:22-jdk-slim

# # The application's jar file
# ARG JAR_FILE=target/HackerNewsAPI-0.0.1-SNAPSHOT.jar

# # Add the application's jar to the container
# COPY ${JAR_FILE} app.jar

# # Run the jar file
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# Use the official maven image with OpenJDK 22 to build the application
# FROM maven:3.8.7-openjdk-22-slim AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Use the official OpenJDK 22 image to run the application
# FROM openjdk:22-jdk-slim
# WORKDIR /app
# COPY --from=build /app/target/HackerNewsAPI-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]

#########################################################

# First stage: Build the application with Maven
FROM eclipse-temurin:17-jdk-ubi9-minimal as build
WORKDIR /app

# Install Maven
RUN microdnf install -y maven

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Use a lightweight JRE to run the application
FROM eclipse-temurin:17-jre-ubi9-minimal
WORKDIR /app
COPY --from=build /app/target/HackerNewsAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
