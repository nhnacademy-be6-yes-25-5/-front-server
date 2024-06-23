# Use the official Maven image with Java 11
FROM maven:3.8.8-eclipse-temurin-21

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY . .

# Package the application
RUN mvn package

# Default command
# prod 설정 추가
CMD ["java", "-jar", "target/FrontServer1-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]