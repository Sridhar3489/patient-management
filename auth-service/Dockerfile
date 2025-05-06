
# Specifying base image to use - Contains runtime, build tools (taken from existing images)

# Image that has JDK 21 and maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Working Dir inside the container
WORKDIR /app

# Copying dependencies (pom.xml) to /app
COPY pom.xml .

# Install dependencies only if there is a change from previous version
RUN mvn dependency:go-offline -B

# Copying source code to src folder
COPY src ./src

# Package everything as single JAR
RUN mvn clean package

# Running the built code
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

# Copying built JAR file as app
COPY --from=builder ./app/target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Exposing port
EXPOSE 4005

ENTRYPOINT ["java", "-jar", "app.jar"]