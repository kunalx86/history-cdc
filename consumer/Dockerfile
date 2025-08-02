# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy the pom.xml and resolve dependencies separately (for better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application (skip tests if not needed)
RUN mvn clean package -DskipTests


# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# JVM options (adjust as needed)
ENV JAVA_OPTS="-Xms128m -Xmx512m"

# Start the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
