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
RUN mvn package -DskipTests

# Second stage: Use a lightweight JRE to run the application
FROM eclipse-temurin:17-jre-ubi9-minimal
WORKDIR /app
COPY --from=build /app/target/HackerNewsAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
