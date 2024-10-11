#!/bin/bash

# Stop the script if any command fails
set -e

# Default environment to 'local' if not provided
ENV=${1:-local}

echo "Building and running application for environment: $ENV"

# Print commands and their arguments as they are executed
set -x

# Clean and package the project
mvn clean compile package -DskipTests -P$ENV -Dspring.profiles.active=$ENV

# Run the Spring Boot application
java -jar -Dspring.profiles.active=$ENV rest/target/*.jar