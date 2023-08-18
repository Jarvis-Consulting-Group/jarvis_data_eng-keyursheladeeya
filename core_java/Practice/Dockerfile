# Use an official Java runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the uber JAR into the container at /app
COPY target/StockQuote2CSV-jar-with-dependencies.jar /app/StockQuote2CSV-jar-with-dependencies.jar

# Run the application when the container starts
CMD ["java", "-jar", "StockQuote2CSV-jar-with-dependencies.jar"]
