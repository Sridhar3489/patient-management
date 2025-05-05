
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

# Specifying working directory in the container
WORKDIR /app

# Copying built JAR file as app
COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

# For HTTP
EXPOSE 4001

# For gRPC
EXPOSE 9001

# Running jar file
ENTRYPOINT ["java", "-jar", "app.jar"]