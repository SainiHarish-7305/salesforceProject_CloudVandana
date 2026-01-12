# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy pom and download dependencies
COPY pom.xml .
RUN apk add --no-cache maven
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose Render port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/*.jar"]
