# Stage 1: Build the application using a Maven image that includes JDK 17
FROM maven:3.8.4-openjdk-17 AS buildstage
WORKDIR /app

# Copy only pom.xml initially to cache dependencies
COPY pom.xml .
COPY src ./src

# Build the application without running tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Adjusted to use a Java 17 JRE for compatibility
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
EXPOSE 8080
COPY --from=buildstage /app/target/api-0.0.1-SNAPSHOT.jar ./api-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]